package com.switchwon.shop.usecase;

import com.switchwon.BaseAdaptorMockSetting;
import com.switchwon.consts.Currency;
import com.switchwon.consts.PayType;
import com.switchwon.event.PurchaseEvent;
import com.switchwon.shop.domain.ShopSellHistory;
import com.switchwon.shop.usecase.dto.EstimateParam;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ShopSellTest extends BaseAdaptorMockSetting {

    @InjectMocks
    private ShopSell shopSell;

    @Test
    public void calculateTest(){
        BigDecimal amount = BigDecimal.valueOf(100);

        Arrays.stream(Currency.values()).forEach(currency -> {
            BigDecimal fee = currency.decimalRefine(shop1.getFeePercent().multiply(amount).divide(BigDecimal.valueOf(100), RoundingMode.FLOOR));

            BigDecimal total = amount.add(fee);

            EstimateParam estimateParam = EstimateParam.builder()
                    .amount(amount)
                    .currency(currency)
                    .shop(shop1)
                    .user(user1)
                    .build();

            BigDecimal calculatedFee = shopSell.calculateFee(estimateParam);
            BigDecimal calculatedTotal = shopSell.calculateTotal(estimateParam);

            assertEquals(fee, calculatedFee);
            assertEquals(total, calculatedTotal);
        });
    }

    @Test
    public void sellTest(){
        BigDecimal buyAmount = BigDecimal.valueOf(1500);
        Currency buyCurrency = Currency.USD;
        PurchaseEvent purchaseEvent = PurchaseEvent.builder()
                .user(user1)
                .amount(buyAmount)
                .currency(buyCurrency)
                .shop(shop1)
                .paymentMethod(PayType.CREDIT_CARD)
                .paymentDetails(payDetails)
                .build();
        String eventId = purchaseEvent.getTransactionId();

        shopSell.sell(purchaseEvent);

        //shop의 물건이 팔리면 shopSellHistory에 기록이 있어야 한다.
        ShopSellHistory shopSellHistory = shopSellHistoryMap.get(eventId);
        assertNotNull(shopSellHistory);

        //shop의 판매금액이 물건가격, 수수료, 토탈금액이 적절하게 잘 기록되어 있어야 한다.
        BigDecimal fee = buyCurrency.decimalRefine(shop1.getFeePercent().multiply(buyAmount).divide(BigDecimal.valueOf(100), RoundingMode.FLOOR));
        BigDecimal total = buyAmount.add(fee);

        assertEquals(shopSellHistory.getAmount(), buyAmount);
        assertEquals(shopSellHistory.getFees(), fee);
        assertEquals(shopSellHistory.getAmountTotal(), total);
    }

}