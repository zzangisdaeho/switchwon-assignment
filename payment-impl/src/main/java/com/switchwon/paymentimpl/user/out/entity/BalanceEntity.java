package com.switchwon.paymentimpl.user.out.entity;

import com.switchwon.consts.Currency;
import com.switchwon.user.domain.Balance;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@IdClass(BalanceEntity.BalanceId.class)
@AllArgsConstructor
public class BalanceEntity extends Balance {

    @Id
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Override
    public Currency getCurrency() {
        return super.getCurrency();
    }

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @Override
    public UserEntity getUser() {
        return (UserEntity) super.getUser();
    }

    @Column(nullable = false)
    @Override
    public BigDecimal getBalance() {
        return super.getBalance();

    }



    @EqualsAndHashCode
    @Getter
    @Setter
    @AllArgsConstructor
    public static class BalanceId implements Serializable {
        private Currency currency;
        private String user;
    }


}
