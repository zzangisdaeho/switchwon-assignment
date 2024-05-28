package com.switchwon.user.usecase;

import com.switchwon.consts.Currency;
import com.switchwon.consts.PayType;
import com.switchwon.user.dto.event.BuyEvent;
import com.switchwon.user.usecase.payment_method.PaymentMethod;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class Buy {

    private final List<PaymentMethod> paymentMethodList;

    public void buy(BuyEvent buyEvent){

        PaymentMethod handler = paymentMethodList.stream().filter(paymentMethod -> paymentMethod.able(PayType.match(buyEvent.getPaymentMethod())))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("payType not matched"));

        handler.pay(buyEvent);


    }
}
