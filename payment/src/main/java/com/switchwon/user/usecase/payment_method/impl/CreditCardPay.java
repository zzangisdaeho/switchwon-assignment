package com.switchwon.user.usecase.payment_method.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.switchwon.consts.PayType;
import com.switchwon.user.adaptor.BalanceChangeHistoryStore;
import com.switchwon.user.adaptor.ChargeHistoryStore;
import com.switchwon.user.adaptor.UserStore;
import com.switchwon.user.domain.Balance;
import com.switchwon.event.PurchaseEvent;
import com.switchwon.user.usecase.payment_method.AbstractPaymentMethod;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

public class CreditCardPay extends AbstractPaymentMethod {
    public CreditCardPay(BalanceChangeHistoryStore balanceChangeHistoryStore, ChargeHistoryStore chargeHistoryStore) {
        super(balanceChangeHistoryStore, chargeHistoryStore);
    }

    @Override
    public boolean able(PayType payType) {
        return payType.equals(PayType.CREDIT_CARD);
    }

    @Override
    protected BigDecimal executeCharge(Balance balance, BigDecimal amount, Object paymentDetails) {
        ObjectMapper objectMapper = new ObjectMapper();
        Card card = objectMapper.convertValue(paymentDetails, Card.class);

        //충전을 위한 결제 로직이 있다고 가정하고 호출했다 치자.
        return amount;
    }

    @Override
    protected BigDecimal executePayment(PurchaseEvent purchaseEvent, Balance balance) {
        BigDecimal buyAmount = purchaseEvent.getAmount();
        return buyAmount;
    }

    @Data
    @ToString
    private class Card {

        private String cardNumber;

        private String date;

        private String cvv;
    }
}
