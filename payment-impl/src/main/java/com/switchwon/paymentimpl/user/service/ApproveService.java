package com.switchwon.paymentimpl.user.service;

import com.switchwon.avro.request.ApprovalRequestDto;
import com.switchwon.avro.response.ApprovalResponseDto;
import com.switchwon.consts.Currency;
import com.switchwon.consts.PayType;
import com.switchwon.event.PurchaseEvent;
import com.switchwon.error.ShopNotFoundException;
import com.switchwon.error.UserNotFoundException;
import com.switchwon.paymentimpl.shop.out.jpa.entity.ShopEntity;
import com.switchwon.paymentimpl.shop.out.jpa.repository.ShopEntityRepository;
import com.switchwon.paymentimpl.user.out.jpa.entity.UserEntity;
import com.switchwon.paymentimpl.user.out.jpa.repository.UserEntityRepository;
import com.switchwon.user.adaptor.PointStore;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ApproveService {

    private final ApplicationEventPublisher applicationEventPublisher;
    private final ShopEntityRepository shopEntityRepository;
    private final UserEntityRepository userEntityRepository;
    private final PointStore pointStoreH2;


    @Transactional
    public ApprovalResponseDto approveEventPublish(ApprovalRequestDto approvalRequestDto){
        UserEntity userEntity = userEntityRepository.findById(approvalRequestDto.getUserId()).orElseThrow(UserNotFoundException::new);
        ShopEntity shopEntity = shopEntityRepository.findById(approvalRequestDto.getMerchantId()).orElseThrow(ShopNotFoundException::new);
        BigDecimal buyAmount = approvalRequestDto.getAmount();
        Currency requestCurrency = Currency.match(approvalRequestDto.getCurrency());

        PurchaseEvent purchaseEvent = PurchaseEvent.builder()
                .user(userEntity)
                .amount(buyAmount)
                .currency(requestCurrency)
                .shop(shopEntity)
                .paymentMethod(PayType.match(approvalRequestDto.getPaymentMethod()))
                .paymentDetails(approvalRequestDto.getPaymentDetails())
                .build();


        applicationEventPublisher.publishEvent(purchaseEvent);

        return ApprovalResponseDto.newBuilder()
                .setPaymentId(purchaseEvent.getTransactionId())
                .setStatus("approved")
                .setAmountTotal(shopEntity.calculateTotalAmount(buyAmount, requestCurrency))
                .setCurrency(approvalRequestDto.getCurrency())
                .setTimestamp(purchaseEvent.getPurchaseResult().getTradeTime().toString())
                .build();
    }
}
