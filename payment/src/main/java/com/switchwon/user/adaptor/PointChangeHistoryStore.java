package com.switchwon.user.adaptor;

import com.switchwon.user.domain.BalanceChangeHistory;

public interface PointChangeHistoryStore {

    BalanceChangeHistory recordHistory(BalanceChangeHistory balanceChangeHistory);
}
