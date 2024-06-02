package com.switchwon.user.usecase.payment_method.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.switchwon.consts.PayType;
import com.switchwon.user.adaptor.PointChangeHistoryStore;
import com.switchwon.user.adaptor.ChargeHistoryStore;
import com.switchwon.user.adaptor.PointStore;
import com.switchwon.user.domain.Point;
import com.switchwon.event.PurchaseEvent;
import com.switchwon.user.usecase.UserCharge;
import com.switchwon.user.usecase.payment_method.AbstractPaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

public class CreditCardPay extends AbstractPaymentMethod {

    public CreditCardPay(PointStore pointStore, PointChangeHistoryStore pointChangeHistoryStore, ChargeHistoryStore chargeHistoryStore, UserCharge userCharge, ObjectMapper objectMapper) {
        super(pointStore, pointChangeHistoryStore, chargeHistoryStore, userCharge, objectMapper);
    }

    @Override
    public boolean able(PayType payType) {
        return payType.equals(PayType.CREDIT_CARD);
    }

    @Override
    protected BigDecimal executeCharge(Point point, BigDecimal amount, Object paymentDetails) {

        Card card = objectMapper.convertValue(paymentDetails, Card.class);

        System.out.println("CreditCardPay.executeCharge");
        System.out.println("card = " + card);
        //충전을 위한 결제 로직이 있다고 가정하고 호출했다 치자.
        return amount;
    }

    @Override
    protected BigDecimal executePayment(PurchaseEvent purchaseEvent, Point point) {
        BigDecimal buyAmount = purchaseEvent.getAmount();

        System.out.println("CreditCardPay.executePayment");

        return buyAmount;
    }

    @Data
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    private static class Card {

        private String cardNumber;

        private String expiryDate;

        private String cvv;
    }
}
