package com.switchwon.user.domain;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    private String userId;

    @Builder.Default
    private List<Balance> balances = new ArrayList<>();

    public List<Balance> balances(){
        return this.balances;
    }

    public List<? extends Balance> getBalances(){
        return balances;
    }

    public void addBalance(Balance balance){
        this.balances.add(balance);
        balance.setUser(this);
    }
}
