package com.switchwon.consts;

import java.util.Arrays;

public enum PayType {

    CREDIT_CARD("creditCard");

    private String payWay;

    PayType(String payWay) {
        this.payWay = payWay;
    }

    public static PayType match(String paymentMethod){
        return Arrays.stream(PayType.values())
                .filter(method -> method.payWay.equalsIgnoreCase(paymentMethod))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid payment method: " + paymentMethod));
    }
}
