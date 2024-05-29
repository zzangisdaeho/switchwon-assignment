package com.switchwon.paymentimpl.shop.out.entity;

import com.switchwon.shop.domain.Shop;
import com.switchwon.shop.domain.ShopSellHistory;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class ShopSellHistoryEntity extends ShopSellHistory {

    @Id
    @Override
    public Long getHistoryNum() {
        return super.getHistoryNum();
    }

    @ManyToOne
    @Override
    public ShopEntity getShop() {
        return (ShopEntity) super.getShop();
    }
}
