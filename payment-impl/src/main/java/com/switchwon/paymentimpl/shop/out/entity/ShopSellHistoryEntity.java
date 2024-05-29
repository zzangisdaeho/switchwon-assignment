package com.switchwon.paymentimpl.shop.out.entity;

import com.switchwon.shop.domain.ShopSellHistory;
import jakarta.persistence.*;

import java.time.ZonedDateTime;

@Entity
public class ShopSellHistoryEntity extends ShopSellHistory {

    @Id
    @Override
    public String getTransactionId() {
        return super.getTransactionId();
    }

    @ManyToOne
    @Override
    public ShopEntity getShop() {
        return (ShopEntity) super.getShop();
    }

    @PreUpdate
    @PrePersist
    public void setDate() {
        super.setEventTime(ZonedDateTime.now());
    }
}
