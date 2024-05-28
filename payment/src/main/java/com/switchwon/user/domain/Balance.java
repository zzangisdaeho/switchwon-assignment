package com.switchwon.user.domain;

import com.switchwon.consts.Currency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Balance {

    private BigDecimal balance;

    private Currency currency;

    private User user;
}
