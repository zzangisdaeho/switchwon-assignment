package com.switchwon.shop.domain;

import com.switchwon.consts.Currency;
import lombok.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Shop {

    private String merchantId;

    private BigDecimal feePercent;

    @Builder.Default
    private List<ShopSellHistory> sellHistoryList = new ArrayList<>();

    public List<? extends ShopSellHistory> getSellHistoryList() {
        return sellHistoryList;
    }

    public List<ShopSellHistory> sellHistoryList(){
        return sellHistoryList;
    }

    public BigDecimal calculateFee(BigDecimal amount, Currency currency){
        return amount.multiply(feePercent).divide(BigDecimal.valueOf(100), currency.getDecimalPoint(), RoundingMode.FLOOR);
    }
}
