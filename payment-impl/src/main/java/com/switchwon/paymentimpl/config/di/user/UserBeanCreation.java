package com.switchwon.paymentimpl.config.di.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.switchwon.user.adaptor.PointChangeHistoryStore;
import com.switchwon.user.adaptor.ChargeHistoryStore;
import com.switchwon.user.adaptor.PointStore;
import com.switchwon.user.adaptor.UserStore;
import com.switchwon.user.usecase.UserBuy;
import com.switchwon.user.usecase.UserInfo;
import com.switchwon.user.usecase.payment_method.PaymentMethod;
import com.switchwon.user.usecase.payment_method.impl.CreditCardPay;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class UserBeanCreation {

    @Bean
    public UserInfo userInfo(UserStore userStoreH2){
        return new UserInfo(userStoreH2);
    }

    @Bean
    public UserBuy userBuy(List<PaymentMethod> paymentMethodList){
        return new UserBuy(paymentMethodList);
    }

    @Bean
    public PaymentMethod creditCardPay(PointStore pointStore, PointChangeHistoryStore pointChangeHistoryStore, ChargeHistoryStore chargeHistoryStore, ObjectMapper objectMapper){
        return new CreditCardPay(pointStore, pointChangeHistoryStore, chargeHistoryStore, objectMapper);
    }
}
