package com.switchwon.user.usecase.payment_method;

import com.switchwon.consts.ChangeReason;
import com.switchwon.consts.Currency;
import com.switchwon.event.PurchaseEvent;
import com.switchwon.user.adaptor.BalanceChangeHistoryStore;
import com.switchwon.user.adaptor.ChargeHistoryStore;
import com.switchwon.user.domain.Balance;
import com.switchwon.user.domain.BalanceChangeHistory;
import com.switchwon.user.domain.ChargeHistory;
import com.switchwon.user.domain.User;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.function.Supplier;

@RequiredArgsConstructor
public abstract class AbstractPaymentMethod implements PaymentMethod {

    protected final BalanceChangeHistoryStore balanceChangeHistoryStore;
    protected final ChargeHistoryStore chargeHistoryStore;

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

    @Override
    public Balance handleCharge(Balance balance, BigDecimal insufficientAmount, Object paymentDetails) {
        ChargeHistory progress = chargeHistoryStore.progress(balance.getUser(), insufficientAmount, balance.getCurrency());

        try {
            // 충전 시작
            BigDecimal chargeAmount = executeCharge(balance, insufficientAmount, paymentDetails);

            // 충전 후 상태 기록
            chargeHistoryStore.updateStatus(progress.getEventId(), ChargeHistory.ChargeStatus.SUCCESS);

            // 충전 금액을 Balance에 반영
            balance.changeBalance(chargeAmount);

            // 충전 성공 기록
            recordBalanceChangeHistory(balance, insufficientAmount, ChangeReason.CHARGE, true);
        } catch (Exception e) {
            // 예외 발생 시 충전 실패 상태 기록
            chargeHistoryStore.updateStatus(progress.getEventId(), ChargeHistory.ChargeStatus.FAIL);
            throw e;
        }

        return balance;
    }

    @Override
    public Balance handlePayment(PurchaseEvent purchaseEvent, Balance balance) {
        BigDecimal payAmount = executePayment(purchaseEvent, balance);
        balance.changeBalance(payAmount.negate());
        recordBalanceChangeHistory(balance, purchaseEvent.getAmount(), ChangeReason.BUY, false);
        return balance;
    }

    protected abstract BigDecimal executeCharge(Balance balance, BigDecimal insufficientAmount, Object paymentDetails);

    protected abstract BigDecimal executePayment(PurchaseEvent purchaseEvent, Balance balance);

    protected void updateBalance(Balance balance, BigDecimal amount) {
        balance.changeBalance(amount);
    }

    protected void recordBalanceChangeHistory(Balance balance, BigDecimal amount, ChangeReason changeReason, boolean plus) {
        BalanceChangeHistory balanceChangeHistory = BalanceChangeHistory.builder()
                .user(balance.getUser())
                .currency(balance.getCurrency())
                .changeAmount(plus ? amount : amount.negate())
                .currentAmount(balance.getBalance())
                .reason(changeReason)
                .build();

        balanceChangeHistoryStore.recordHistory(balanceChangeHistory);
    }

}