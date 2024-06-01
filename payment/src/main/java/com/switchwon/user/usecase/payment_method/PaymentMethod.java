package com.switchwon.user.usecase.payment_method;

import com.switchwon.consts.Currency;
import com.switchwon.consts.PayType;
import com.switchwon.user.domain.Point;
import com.switchwon.user.domain.User;
import com.switchwon.event.PurchaseEvent;

import java.math.BigDecimal;

public interface PaymentMethod {

    public boolean able(PayType payType);

    default Point processPayment(PurchaseEvent purchaseEvent) {
        User user = purchaseEvent.getUser();
        Point currencyPoint = getUserBalance(user, purchaseEvent.getCurrency());

        if (currencyPoint.getBalance().compareTo(purchaseEvent.getAmount()) < 0) {
            BigDecimal insufficientAmount = purchaseEvent.getShop().calculateTotalAmount(purchaseEvent.getAmount(), currencyPoint.getCurrency()).subtract(currencyPoint.getBalance());
            currencyPoint = handleCharge(purchaseEvent.getTransactionId(), currencyPoint, insufficientAmount, purchaseEvent.getPaymentDetails());
        }

        return handlePayment(purchaseEvent, currencyPoint);
    }

    Point getUserBalance(User user, Currency currency);

    Point handleCharge(String eventId, Point point, BigDecimal amount, Object paymentDetails);

    Point handlePayment(PurchaseEvent purchaseEvent, Point point);

}
