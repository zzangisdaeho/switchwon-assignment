package com.switchwon.user.usecase;

import com.switchwon.event.PurchaseEvent;
import com.switchwon.user.domain.Point;
import com.switchwon.user.usecase.payment_method.PaymentMethod;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class UserBuy {

    private final List<PaymentMethod> paymentMethodList;

    public void buy(PurchaseEvent purchaseEvent){

        PaymentMethod handler = paymentMethodList.stream().filter(paymentMethod -> paymentMethod.able(purchaseEvent.getPaymentMethod()))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("payType not matched"));

        Point point = handler.processPayment(purchaseEvent);
    }
}
