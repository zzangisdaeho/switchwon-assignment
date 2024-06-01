package com.switchwon.paymentimpl.user.out.jpa.entity;

import com.switchwon.consts.Currency;
import com.switchwon.user.domain.Point;
import com.switchwon.user.domain.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@IdClass(PointEntity.PointId.class)
@SuperBuilder
@NoArgsConstructor
public class PointEntity extends Point {

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

//    @Id
//    @ManyToOne
//    @JoinColumn(name = "user_id", nullable = false)
//    private UserEntity user;

    @Column(nullable = false)
    @Override
    public BigDecimal getBalance() {
        return super.getBalance();
    }

    @Override
    public void setUser(User user) {
        super.setUser(user);
//        this.user = (UserEntity) user;
    }

    @EqualsAndHashCode
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PointId implements Serializable {
        private Currency currency;
        private String user;
    }


}
