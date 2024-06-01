package com.switchwon.user.domain;

import com.switchwon.consts.Currency;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@SuperBuilder
public class Point {

    private BigDecimal balance;

    private Currency currency;

    private User user;

    public void changeBalance(BigDecimal amount) {
        this.balance = balance.add(amount);
    }
}
