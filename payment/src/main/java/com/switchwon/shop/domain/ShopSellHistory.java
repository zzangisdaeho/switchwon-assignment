package com.switchwon.shop.domain;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

public class ShopSellHistory {

    private String merchantId;

    private String userId;

    private BigDecimal amount;

    private BigDecimal fees;

    private BigDecimal amountTotal;

    private ZonedDateTime eventTime;
}
