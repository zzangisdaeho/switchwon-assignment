package com.switchwon.event;

import com.switchwon.consts.Currency;
import com.switchwon.consts.PayType;
import com.switchwon.shop.domain.Shop;
import com.switchwon.user.domain.User;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class PurchaseEvent {

    private String transactionId = UUID.randomUUID().toString();
    private User user;
    private BigDecimal amount;
    private Currency currency;
    private Shop shop;
    private PayType paymentMethod;

    private Object paymentDetails;
}
