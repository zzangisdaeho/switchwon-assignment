package com.switchwon.user.domain;

import com.switchwon.consts.ChangeReason;
import com.switchwon.consts.Currency;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class BalanceChangeHistory {

    private Long changeNo;

    private String eventId;

    private User user;

    private Currency currency;

    private BigDecimal beforeChangeAmount;

    private BigDecimal changeAmount;

    private BigDecimal afterChangeAmount;

    private ChangeReason reason;

    private ZonedDateTime eventTime;

}
