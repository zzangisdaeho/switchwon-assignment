package com.switchwon.shop.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Shop {

    private String merchantId;

    private BigDecimal feePercent;

    private List<ShopSellHistory> sellHistoryList = new ArrayList<>();

    private List<>

    public List<? extends ShopSellHistory> getSellHistoryList() {
        return sellHistoryList;
    }
}
