package com.switchwon.shop.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ShopSellHistory {

    private String eventId;

    private Shop shop;

    private String userId;

    private BigDecimal amount;

    private BigDecimal fees;

    private BigDecimal amountTotal;

    private ZonedDateTime eventTime;
}
