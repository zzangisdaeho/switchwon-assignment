package com.switchwon.user.usecase.payment_method;

import com.switchwon.consts.Currency;
import com.switchwon.consts.PayType;
import com.switchwon.shop.domain.Shop;
import com.switchwon.user.domain.Balance;
import com.switchwon.user.domain.User;
import com.switchwon.user.dto.event.BuyEvent;

public interface PaymentMethod {

    public boolean able(PayType payType);

    public Balance pay(BuyEvent buyEvent);

}
