package com.switchwon.user.adaptor;

import com.switchwon.consts.Currency;
import com.switchwon.user.domain.Balance;
import com.switchwon.user.domain.User;

public interface BalanceChanger {

    Balance adjustBalance(User user, Currency currency);
}
