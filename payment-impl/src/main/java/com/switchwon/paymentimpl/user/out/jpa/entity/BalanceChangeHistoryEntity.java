package com.switchwon.paymentimpl.user.out.jpa.entity;

import com.switchwon.consts.ChangeReason;
import com.switchwon.consts.Currency;
import com.switchwon.user.domain.BalanceChangeHistory;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Entity
@SuperBuilder
@NoArgsConstructor
public class BalanceChangeHistoryEntity extends BalanceChangeHistory {

    @Id
    @GeneratedValue
    @Override
    public Long getChangeNo() {
        return super.getChangeNo();
    }

    @Override
    public String getEventId() {
        return super.getEventId();
    }

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @Override
    public UserEntity getUser() {
        return (UserEntity) super.getUser();
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "currency", nullable = false)
    @Override
    public Currency getCurrency() {
        return super.getCurrency();
    }

    @Column(name = "change_amount", nullable = false)
    @Override
    public BigDecimal getChangeAmount() {
        return super.getChangeAmount();
    }

    @Override
    public BigDecimal getBeforeChangeAmount() {
        return super.getBeforeChangeAmount();
    }

    @Override
    public BigDecimal getAfterChangeAmount() {
        return super.getAfterChangeAmount();
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "reason", nullable = false)
    @Override
    public ChangeReason getReason() {
        return super.getReason();
    }

    @Column(name = "event_time", nullable = false)
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
