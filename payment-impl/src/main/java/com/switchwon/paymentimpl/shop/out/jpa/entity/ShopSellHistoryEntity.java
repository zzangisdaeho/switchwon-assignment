package com.switchwon.paymentimpl.shop.out.jpa.entity;

import com.switchwon.shop.domain.ShopSellHistory;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Entity
@NoArgsConstructor
@SuperBuilder
public class ShopSellHistoryEntity extends ShopSellHistory {

    @Id
    @Override
    public String getEventId() {
        return super.getEventId();
    }

    @ManyToOne
    @Override
    @JoinColumn(name = "merchant_id", nullable = false)
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
