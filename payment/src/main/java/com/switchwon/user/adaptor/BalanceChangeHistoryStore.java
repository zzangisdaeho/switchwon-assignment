package com.switchwon.user.adaptor;

import com.switchwon.consts.ChangeReason;
import com.switchwon.consts.Currency;
import com.switchwon.user.domain.BalanceChangeHistory;
import com.switchwon.user.domain.User;

public interface BalanceChangeHistoryStore {

    BalanceChangeHistory recordHistory(BalanceChangeHistory balanceChangeHistory);
}
