package com.switchwon.user.domain;

import com.switchwon.consts.Currency;
import lombok.*;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
public class Balance {

    private BigDecimal balance;

    private Currency currency;

    private User user;

    public void changeBalance(BigDecimal amount) {
        this.balance = balance.add(amount);
    }

    public void setUser(User user){
        this.user = user;
        user.balances().add(this);
    }

}
