package com.switchwon.user.usecase.payment_method;

import com.switchwon.consts.Currency;
import com.switchwon.consts.PayType;
import com.switchwon.user.domain.Balance;
import com.switchwon.user.domain.BalanceChangeHistory;
import com.switchwon.user.domain.User;
import com.switchwon.event.PurchaseEvent;

import java.math.BigDecimal;

public interface PaymentMethod {

    public boolean able(PayType payType);

    default Balance processPayment(PurchaseEvent purchaseEvent) {
        User user = purchaseEvent.getUser();
        Balance currencyBalance = getUserBalance(user, purchaseEvent.getCurrency());

        if (currencyBalance.getBalance().compareTo(purchaseEvent.getAmount()) < 0) {
            BigDecimal insufficientAmount = purchaseEvent.getAmount().subtract(currencyBalance.getBalance());
            currencyBalance = charge(currencyBalance, insufficientAmount, purchaseEvent.getPaymentDetails());
            recordBalanceChangeHistory(currencyBalance, insufficientAmount, true);
        }

        Balance balance = executePayment(purchaseEvent, currencyBalance);
        recordBalanceChangeHistory(currencyBalance, purchaseEvent.getAmount(), false);
        return balance;
    }

    Balance getUserBalance(User user, Currency currency);

    Balance charge(Balance balance, BigDecimal amount, Object paymentDetails);

    Balance executePayment(PurchaseEvent purchaseEvent, Balance balance);

    BalanceChangeHistory recordBalanceChangeHistory(Balance balance, BigDecimal amount, boolean plus);

}
