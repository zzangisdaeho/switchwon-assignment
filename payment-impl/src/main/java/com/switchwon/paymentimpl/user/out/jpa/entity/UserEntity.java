package com.switchwon.paymentimpl.user.out.jpa.entity;

import com.switchwon.user.domain.Point;
import com.switchwon.user.domain.User;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@SuperBuilder
@NoArgsConstructor
public class UserEntity extends User {

    @Id
    @Override
    public String getUserId() {
        return super.getUserId();
    }

//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
//    @Override
//    public List<BalanceEntity> getBalanceList() {
//        return super.getBalanceList().stream().map(balance -> (BalanceEntity) balance).toList();
//    }

    @Override
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    public List<PointEntity> getPoints() {
        return super.getPoints().stream()
                .map(balance -> (PointEntity) balance)
                .collect(Collectors.toList());
    }

    @Override
    public void addPoint(Point point) {
        super.addPoint(point);
    }
}

