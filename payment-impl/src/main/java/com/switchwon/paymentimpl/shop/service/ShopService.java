package com.switchwon.paymentimpl.shop.service;

import com.switchwon.avro.response.EstimateResponseDto;
import com.switchwon.consts.Currency;
import com.switchwon.event.PurchaseEvent;
import com.switchwon.error.ShopNotFoundException;
import com.switchwon.error.UserNotFoundException;
import com.switchwon.paymentimpl.shop.out.jpa.repository.ShopEntityRepository;
import com.switchwon.paymentimpl.user.out.jpa.repository.UserEntityRepository;
import com.switchwon.shop.usecase.ShopSell;
import com.switchwon.shop.usecase.dto.EstimateParam;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ShopService {
    
    private final ShopSell shopSell;

    private final ShopEntityRepository shopEntityRepository;
    private final UserEntityRepository userEntityRepository;
    
    @Transactional(readOnly = true)
    public EstimateResponseDto priceEstimate(BigDecimal amount, String currency, String merchantId, String userId){

        EstimateParam estimateParam = new EstimateParam(amount, Currency.match(currency),
                shopEntityRepository.findById(merchantId).orElseThrow(ShopNotFoundException::new),
                userEntityRepository.findById(userId).orElseThrow(UserNotFoundException::new));

        BigDecimal fee = shopSell.calculateFee(estimateParam);
        BigDecimal total = shopSell.calculateTotal(estimateParam);

        return EstimateResponseDto.newBuilder()
                .setCurrency(currency)
                .setFees(fee)
                .setEstimatedTotal(total)
                .build();
    }

    @EventListener
    @Transactional
    @Order(2)
    public void shopSell(PurchaseEvent purchaseEvent){
        System.out.println("ShopService.shopSell");
        shopSell.sell(purchaseEvent);
    }
}
