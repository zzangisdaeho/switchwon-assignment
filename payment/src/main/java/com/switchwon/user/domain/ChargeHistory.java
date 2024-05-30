package com.switchwon.user.domain;

import com.switchwon.consts.Currency;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Getter
@Setter
public class ChargeHistory {

    private String eventId;

    private User user;

    private ChargeStatus chargeStatus;

    private Currency currency;

    private BigDecimal chargeAmount;

    private ZonedDateTime eventTime;

    public enum ChargeStatus{
        PROGRESS, FAIL, SUCCESS
    }

}
