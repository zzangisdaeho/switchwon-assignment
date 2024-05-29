package com.switchwon.shop.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Getter
@Setter
public class ShopSellHistory {

    private Long historyNum;

    private Shop shop;

    private String userId;

    private BigDecimal amount;

    private BigDecimal fees;

    private BigDecimal amountTotal;

    private ZonedDateTime eventTime;
}
