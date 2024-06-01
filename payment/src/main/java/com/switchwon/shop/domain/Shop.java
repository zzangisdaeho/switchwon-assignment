package com.switchwon.shop.domain;

import com.switchwon.consts.Currency;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
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
        return currency.decimalRefine(amount.multiply(feePercent).divide(BigDecimal.valueOf(100), currency.getDecimalPoint(), RoundingMode.FLOOR));
    }

    public BigDecimal calculateTotalAmount(BigDecimal amount, Currency currency){
        BigDecimal fee = this.calculateFee(amount, currency);
        return currency.decimalRefine(amount.add(fee));
    }

    public void addShopSellHistory(ShopSellHistory sellHistory){
        this.sellHistoryList.add(sellHistory);
        sellHistory.setShop(this);
    }
}
