package com.switchwon.paymentimpl.shop.out.entity;

import com.switchwon.shop.domain.ShopSellHistory;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Entity
public class ShopSellHistoryEntity extends ShopSellHistory {

    @Id
    @Override
    public String getEventId() {
        return super.getEventId();
    }

    @ManyToOne
    @Override
    public ShopEntity getShop() {
        return (ShopEntity) super.getShop();
    }

    @Override
    public String getUserId() {
        return super.getUserId();
    }

    @Override
    public BigDecimal getAmount() {
        return super.getAmount();
    }

    @Override
    public BigDecimal getFees() {
        return super.getFees();
    }

    @Override
    public BigDecimal getAmountTotal() {
        return super.getAmountTotal();
    }

    @Override
    public ZonedDateTime getEventTime() {
        return super.getEventTime();
    }

    @PreUpdate
    @PrePersist
    public void setDate() {
        super.setEventTime(ZonedDateTime.now());
    }
}
