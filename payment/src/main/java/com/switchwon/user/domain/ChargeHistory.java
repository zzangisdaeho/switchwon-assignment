package com.switchwon.user.domain;

import com.switchwon.consts.Currency;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class ChargeHistory {

    private String eventId;

    private User user;

    private ChargeStatus chargeStatus;

    private Currency currency;

    private BigDecimal chargeAmount;

    private ZonedDateTime eventTime;

    private Object chargeBy;

    public enum ChargeStatus{
        PROGRESS, CHARGE_FAIL, CHARGE_SUCCESS, POINT_UPDATE_FAIL, FINISH
    }

    public void updateStatus(ChargeStatus chargeStatus){
        this.chargeStatus = chargeStatus;
    }

}
