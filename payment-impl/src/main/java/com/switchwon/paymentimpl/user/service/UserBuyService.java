package com.switchwon.paymentimpl.user.service;

import com.switchwon.event.PurchaseEvent;
import com.switchwon.user.usecase.UserBuy;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserBuyService {

    private final UserBuy userBuy;

    @Transactional
    @EventListener
    @Order(1)
    public void userBuy(PurchaseEvent purchaseEvent){
        System.out.println("UserBuyService.userBuy");
        userBuy.buy(purchaseEvent);
    }
}
