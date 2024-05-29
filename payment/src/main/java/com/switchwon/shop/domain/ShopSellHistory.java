package com.switchwon.shop.domain;

import lombok.*;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShopSellHistory {

    private String transactionId;

    private Shop shop;

    private String userId;

    private BigDecimal amount;

    private BigDecimal fees;

    private BigDecimal amountTotal;

    private ZonedDateTime eventTime;
}
