package com.switchwon.user.adaptor;

import com.switchwon.consts.Currency;
import com.switchwon.user.domain.ChargeHistory;
import com.switchwon.user.domain.User;

import java.math.BigDecimal;

public interface ChargeHistoryStore {

    //새로운 트랜잭션으로 시작
    ChargeHistory progress(User user, BigDecimal amount, Currency currency);

    //새로운 트랜잭션으로 시작
    ChargeHistory updateStatus(String eventId, ChargeHistory.ChargeStatus chargeStatus);
}
