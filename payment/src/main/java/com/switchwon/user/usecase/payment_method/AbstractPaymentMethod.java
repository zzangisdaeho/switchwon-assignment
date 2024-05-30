package com.switchwon.user.usecase.payment_method;

import com.switchwon.consts.ChangeReason;
import com.switchwon.consts.Currency;
import com.switchwon.user.adaptor.BalanceChangeHistoryStore;
import com.switchwon.user.adaptor.UserStore;
import com.switchwon.user.domain.Balance;
import com.switchwon.user.domain.BalanceChangeHistory;
import com.switchwon.user.domain.User;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.function.Supplier;

@RequiredArgsConstructor
public abstract class AbstractPaymentMethod implements PaymentMethod {

    protected final UserStore userStore;
    protected final BalanceChangeHistoryStore balanceChangeHistoryStore;

    @Override
    public Balance getUserBalance(User user, Currency currency) {
        return user.balances().stream()
                .filter(balance -> balance.getCurrency().equals(currency))
                .findFirst()
                .orElseGet(createNewBalanceType(user, currency));
    }

    private Supplier<Balance> createNewBalanceType(User user, Currency currency) {
        return () -> {
            Balance build = Balance.builder()
                    .balance(BigDecimal.ZERO)
                    .currency(currency)
                    .build();
            user.addBalance(build);
            return build;
        };
    }

    public BalanceChangeHistory recordBalanceChangeHistory(Balance balance, BigDecimal amount, ChangeReason changeReason, boolean plus) {
        BalanceChangeHistory balanceChangeHistory = BalanceChangeHistory.builder()
                .user(balance.getUser())
                .currency(balance.getCurrency())
                .changeAmount(plus? balance.getBalance().subtract(amount) : balance.getBalance().add(amount))
                .currentAmount(balance.getBalance())
                .reason(changeReason)
                .build();

        return balanceChangeHistoryStore.recordHistory(balanceChangeHistory);
    }

}