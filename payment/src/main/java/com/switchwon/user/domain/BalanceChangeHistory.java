package com.switchwon.user.domain;

import com.switchwon.consts.ChangeReason;
import com.switchwon.consts.Currency;
import lombok.*;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BalanceChangeHistory {

    private Long historyNum;

    private User user;

    private Currency currency;

    private BigDecimal changeAmount;

    private BigDecimal currentAmount;

    private ChangeReason reason;

    private ZonedDateTime eventTime;

}
