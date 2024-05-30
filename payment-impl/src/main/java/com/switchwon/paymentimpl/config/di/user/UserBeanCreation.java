package com.switchwon.paymentimpl.config.di.user;

import com.switchwon.paymentimpl.user.out.h2.UserStoreH2;
import com.switchwon.user.usecase.UserInfo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserBeanCreation {

    @Bean
    public UserInfo userInfo(UserStoreH2 userStoreH2){
        return new UserInfo(userStoreH2);
    }
}
