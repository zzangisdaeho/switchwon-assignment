package com.switchwon.shop.usecase;

import com.switchwon.event.PurchaseEvent;
import com.switchwon.shop.adaptor.ShopSellHistoryStore;
import com.switchwon.shop.domain.ShopSellHistory;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@RequiredArgsConstructor
public class ShopSell {

    private final ShopSellHistoryStore shopSellHistoryStore;

    public void sell(PurchaseEvent purchaseEvent){
        BigDecimal fee = purchaseEvent.getShop().calculateFee(purchaseEvent.getAmount(), purchaseEvent.getCurrency());
        ShopSellHistory shopSellHistory = ShopSellHistory.builder()
                .transactionId(purchaseEvent.getTransactionId())
                .shop(purchaseEvent.getShop())
                .userId(purchaseEvent.getUser().getUserId())
                .amount(purchaseEvent.getAmount())
                .fees(fee)
                .amountTotal(purchaseEvent.getAmount().add(fee))
                .build();

        shopSellHistory = shopSellHistoryStore.save(shopSellHistory);
    }
}
