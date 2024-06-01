package com.switchwon.event;

import com.switchwon.consts.Currency;
import com.switchwon.consts.PayType;
import com.switchwon.shop.domain.Shop;
import com.switchwon.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@Builder
public class PurchaseEvent {

    @Builder.Default
    private String transactionId = UUID.randomUUID().toString();
    private User user;
    private BigDecimal amount;
    private Currency currency;
    private Shop shop;
    private PayType paymentMethod;

    private Object paymentDetails;

    @Builder.Default
    private PurchaseResult purchaseResult = new PurchaseResult();

    @Data
    public static class PurchaseResult {

        private ZonedDateTime tradeTime;
    }
}
