package com.switchwon.paymentimpl.user.out.jpa.entity;

import com.switchwon.user.domain.User;
import jakarta.persistence.*;

import java.util.List;
import java.util.stream.Collectors;

@Entity
public class UserEntity extends User {

    @Id
    @Override
    public String getUserId() {
        return super.getUserId();
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<BalanceEntity> getBalances() {
        return super.getBalances().stream()
                .map(balance -> (BalanceEntity) balance)
                .collect(Collectors.toList());
    }



}

