package com.switchwon.user.usecase;

import com.switchwon.consts.ChangeReason;
import com.switchwon.user.adaptor.ChargeHistoryStore;
import com.switchwon.user.adaptor.PointChangeHistoryStore;
import com.switchwon.user.adaptor.PointStore;
import com.switchwon.user.domain.BalanceChangeHistory;
import com.switchwon.user.domain.ChargeHistory;
import com.switchwon.user.domain.Point;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class UserCharge {

    private final PointStore pointStore;
    private final PointChangeHistoryStore pointChangeHistoryStore;
    private final ChargeHistoryStore chargeHistoryStore;

    public Point handleCharge(String eventId, Point point, BigDecimal insufficientAmount, Object paymentDetails, ChargeExecutor chargeExecutor) {
        ChargeHistory progress = chargeHistoryStore.progress(eventId, point.getUser(), insufficientAmount, point.getCurrency(), paymentDetails);
        BigDecimal chargeAmount;
        try {
            // 충전 시작
            chargeAmount = chargeExecutor.executeCharge(point, insufficientAmount, paymentDetails);

            // 충전 후 상태 기록
            chargeHistoryStore.updateStatus(progress.getEventId(), ChargeHistory.ChargeStatus.CHARGE_SUCCESS);

        } catch (Exception e) {
            // 예외 발생 시 충전 실패 상태 기록
            chargeHistoryStore.updateStatus(progress.getEventId(), ChargeHistory.ChargeStatus.CHARGE_FAIL);
            throw e;
        }

        try{
            // 충전 금액을 Balance에 반영
            point = pointStore.updateBalance(point.getUser(), point.getCurrency(), chargeAmount);

        }catch (Exception e){
            // 예외 발생 시 balance update 실패 상태 기록
            chargeHistoryStore.updateStatus(progress.getEventId(), ChargeHistory.ChargeStatus.POINT_UPDATE_FAIL);
        }

        // 충전 최종 성공 기록
        chargeHistoryStore.updateStatus(progress.getEventId(), ChargeHistory.ChargeStatus.FINISH);

        // 충전 성공 기록
        recordBalanceChangeHistory(eventId, point, insufficientAmount, ChangeReason.CHARGE, true);

        return point;
    }

    public void recordBalanceChangeHistory(String eventId, Point point, BigDecimal amount, ChangeReason changeReason, boolean plus) {
        BalanceChangeHistory balanceChangeHistory = BalanceChangeHistory.builder()
                .eventId(eventId)
                .user(point.getUser())
                .currency(point.getCurrency())
                .beforeChangeAmount(plus? point.getBalance().add(amount.negate()) : point.getBalance().add(amount))
                .changeAmount(plus ? amount : amount.negate())
                .afterChangeAmount(point.getBalance())
                .reason(changeReason)
                .build();

        pointChangeHistoryStore.recordHistory(balanceChangeHistory);
    }

    @FunctionalInterface
    public interface ChargeExecutor {
        BigDecimal executeCharge(Point point, BigDecimal amount, Object paymentDetails);
    }
}
