package com.switchwon.user.usecase;

import com.switchwon.event.PurchaseEvent;
import com.switchwon.user.domain.Point;
import com.switchwon.user.usecase.payment_method.PaymentMethod;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class UserBuy {

    private final List<PaymentMethod> paymentMethodList;

    public Point buy(PurchaseEvent purchaseEvent){

        PaymentMethod handler = paymentMethodList.stream().filter(paymentMethod -> paymentMethod.able(purchaseEvent.getPaymentMethod()))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("payType not matched"));

        return handler.processPayment(purchaseEvent);
    }
}
