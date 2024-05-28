package com.switchwon.consts;

import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;

@Getter
public enum Currency {

    USD(2, "USD"), KRW(0, "KRW");

    private final int decimalPoint;
    private final String currencyString;

    Currency(int decimalPoint, String currencyString) {
        this.decimalPoint = decimalPoint;
        this.currencyString = currencyString;
    }

    public BigDecimal decimalRefine(BigDecimal amount){
        return amount.setScale(decimalPoint, RoundingMode.FLOOR);
    }

    public static Currency match(String currencyString){
        return Arrays.stream(Currency.values())
                .filter(method -> method.currencyString.equals(currencyString))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid payment method: " + currencyString));
    }
}
