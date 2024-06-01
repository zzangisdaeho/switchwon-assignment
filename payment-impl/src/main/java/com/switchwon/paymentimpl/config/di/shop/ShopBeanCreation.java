package com.switchwon.paymentimpl.config.di.shop;

import com.switchwon.shop.adaptor.ShopSellHistoryStore;
import com.switchwon.shop.usecase.ShopSell;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShopBeanCreation {

    @Bean
    public ShopSell shopSell(ShopSellHistoryStore shopSellHistoryStore){
        return new ShopSell(shopSellHistoryStore);
    }
}
