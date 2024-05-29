package com.switchwon.paymentimpl.user.out.entity;

import com.switchwon.consts.ChangeReason;
import com.switchwon.consts.Currency;
import com.switchwon.user.domain.BalanceChangeHistory;
import com.switchwon.user.domain.User;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Entity
@Table(name = "balance_change_history")
public class BalanceChangeHistoryEntity extends BalanceChangeHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Override
    public Long getHistoryNum() {
        return super.getHistoryNum();
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

    @Column(name = "current_amount", nullable = false)
    @Override
    public BigDecimal getCurrentAmount() {
        return super.getCurrentAmount();
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
