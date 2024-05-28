package com.switchwon.user.domain;

import com.switchwon.consts.ChangeReason;
import com.switchwon.consts.Currency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BalanceChangeHistory {

    private String userId;

    private Currency currency;

    private BigDecimal changeAmount;

    private BigDecimal currentAmount;

    private ChangeReason reason;

    private ZonedDateTime eventTime;
}
