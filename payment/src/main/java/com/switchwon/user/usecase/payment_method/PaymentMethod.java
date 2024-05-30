package com.switchwon.user.usecase.payment_method;

import com.switchwon.consts.ChangeReason;
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
            currencyBalance = handleCharge(currencyBalance, insufficientAmount, purchaseEvent.getPaymentDetails());
        }

        return handlePayment(purchaseEvent, currencyBalance);
    }

    Balance getUserBalance(User user, Currency currency);

    Balance handleCharge(Balance balance, BigDecimal amount, Object paymentDetails);

    Balance handlePayment(PurchaseEvent purchaseEvent, Balance balance);

}
