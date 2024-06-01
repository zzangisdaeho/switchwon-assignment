package com.switchwon.shop.usecase;

import com.switchwon.event.PurchaseEvent;
import com.switchwon.shop.adaptor.ShopSellHistoryStore;
import com.switchwon.shop.domain.ShopSellHistory;
import com.switchwon.shop.usecase.dto.EstimateParam;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class ShopSell {

    private final ShopSellHistoryStore shopSellHistoryStore;

    public void sell(PurchaseEvent purchaseEvent){
        BigDecimal fee = purchaseEvent.getShop().calculateFee(purchaseEvent.getAmount(), purchaseEvent.getCurrency());
        ShopSellHistory shopSellHistory = ShopSellHistory.builder()
                .eventId(purchaseEvent.getTransactionId())
                .shop(purchaseEvent.getShop())
                .userId(purchaseEvent.getUser().getUserId())
                .amount(purchaseEvent.getAmount())
                .fees(fee)
                .amountTotal(purchaseEvent.getAmount().add(fee))
                .build();

        shopSellHistory = shopSellHistoryStore.save(shopSellHistory);

        purchaseEvent.getPurchaseResult().setTradeTime(shopSellHistory.getEventTime());
    }

    public BigDecimal calculateFee(EstimateParam estimateParam){
        return estimateParam.getShop().calculateFee(estimateParam.getAmount(), estimateParam.getCurrency());
    }

    public BigDecimal calculateTotal(EstimateParam estimateParam){
        return estimateParam.getShop().calculateTotalAmount(estimateParam.getAmount(), estimateParam.getCurrency());
    }
}
