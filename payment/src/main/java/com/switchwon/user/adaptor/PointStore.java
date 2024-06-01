package com.switchwon.user.adaptor;

import com.switchwon.consts.Currency;
import com.switchwon.user.domain.Point;
import com.switchwon.user.domain.User;

import java.math.BigDecimal;

public interface PointStore {

    Point findElseCreate(User user, Currency currency);

    //새로운 트랜잭션으로 시작
    Point updateBalance(User user, Currency currency, BigDecimal changeAmount);

}
