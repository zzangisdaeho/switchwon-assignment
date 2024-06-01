package com.switchwon.paymentimpl.user.out.jpa.entity;

import com.switchwon.consts.Currency;
import com.switchwon.paymentimpl.util.JsonAttributeConverter;
import com.switchwon.user.domain.ChargeHistory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Entity
@SuperBuilder
@NoArgsConstructor
public class ChargeHistoryEntity extends ChargeHistory {

    @Id
    @Override
    public String getEventId() {
        return super.getEventId();
    }

    @ManyToOne
    @Override
    public UserEntity getUser() {
        return (UserEntity) super.getUser();
    }

    @Enumerated(EnumType.STRING)
    @Override
    public ChargeStatus getChargeStatus() {
        return super.getChargeStatus();
    }

    @Enumerated(EnumType.STRING)
    @Override
    public Currency getCurrency() {
        return super.getCurrency();
    }

    @Override
    public BigDecimal getChargeAmount() {
        return super.getChargeAmount();
    }

    @Override
    public ZonedDateTime getEventTime() {
        return super.getEventTime();
    }

    @Convert(converter = JsonAttributeConverter.class)
    @Override
    public Object getChargeBy() {
        return super.getChargeBy();
    }

    @PreUpdate
    @PrePersist
    public void setDate() {
        super.setEventTime(ZonedDateTime.now());
    }
}
