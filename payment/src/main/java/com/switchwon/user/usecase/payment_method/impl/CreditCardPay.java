package com.switchwon.user.usecase.payment_method.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.switchwon.consts.PayType;
import com.switchwon.user.adaptor.BalanceChangeHistoryStore;
import com.switchwon.user.adaptor.UserStore;
import com.switchwon.user.domain.Balance;
import com.switchwon.event.PurchaseEvent;
import com.switchwon.user.usecase.payment_method.AbstractPaymentMethod;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

public class CreditCardPay extends AbstractPaymentMethod {
    public CreditCardPay(UserStore userStore, BalanceChangeHistoryStore balanceChangeHistoryStore) {
        super(userStore, balanceChangeHistoryStore);
    }

    @Override
    public boolean able(PayType payType) {
        return payType.equals(PayType.CREDIT_CARD);
    }

    @Override
    public Balance charge(Balance balance, BigDecimal amount, Object paymentDetails) {

        ObjectMapper objectMapper = new ObjectMapper();
        Card card = objectMapper.convertValue(paymentDetails, Card.class);

        // 신용카드 정보로 충전하는 로직이 있다고 가정.

        balance.changeBalance(amount);

        return balance;
    }

    @Override
    public Balance executePayment(PurchaseEvent purchaseEvent, Balance balance) {
        // 실제 결제 로직
        BigDecimal buyAmount = purchaseEvent.getAmount();
        balance.changeBalance(buyAmount.negate());
        return balance;
    }

    @Data
    @ToString
    private class Card {

        private String cardNumber;

        private String date;

        private String cvv;
    }
}
