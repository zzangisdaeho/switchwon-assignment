package com.switchwon.user.usecase.payment_method;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.switchwon.consts.ChangeReason;
import com.switchwon.consts.Currency;
import com.switchwon.event.PurchaseEvent;
import com.switchwon.user.adaptor.PointChangeHistoryStore;
import com.switchwon.user.adaptor.ChargeHistoryStore;
import com.switchwon.user.adaptor.PointStore;
import com.switchwon.user.domain.Point;
import com.switchwon.user.domain.BalanceChangeHistory;
import com.switchwon.user.domain.ChargeHistory;
import com.switchwon.user.domain.User;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
public abstract class AbstractPaymentMethod implements PaymentMethod {

    protected final PointStore pointStore;
    protected final PointChangeHistoryStore pointChangeHistoryStore;
    protected final ChargeHistoryStore chargeHistoryStore;
    protected final ObjectMapper objectMapper;

    @Override
    public Point getUserBalance(User user, Currency currency) {
        return pointStore.findElseCreate(user, currency);
    }



    @Override
    public Point handleCharge(String eventId, Point point, BigDecimal insufficientAmount, Object paymentDetails) {
        ChargeHistory progress = chargeHistoryStore.progress(eventId, point.getUser(), insufficientAmount, point.getCurrency(), paymentDetails);
        BigDecimal chargeAmount;
        try {
            // 충전 시작
            chargeAmount = executeCharge(point, insufficientAmount, paymentDetails);

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

    @Override
    public Point handlePayment(PurchaseEvent purchaseEvent, Point point) {
        BigDecimal totalPay = purchaseEvent.getShop().calculateTotalAmount(purchaseEvent.getAmount(), purchaseEvent.getCurrency());
        BigDecimal payAmount = executePayment(purchaseEvent, point);
        point = pointStore.updateBalance(purchaseEvent.getUser(), purchaseEvent.getCurrency(), totalPay.negate());
        recordBalanceChangeHistory(purchaseEvent.getTransactionId(), point, totalPay, ChangeReason.BUY, false);
        return point;
    }

    protected abstract BigDecimal executeCharge(Point point, BigDecimal insufficientAmount, Object paymentDetails);

    protected abstract BigDecimal executePayment(PurchaseEvent purchaseEvent, Point point);

    protected void recordBalanceChangeHistory(String eventId, Point point, BigDecimal amount, ChangeReason changeReason, boolean plus) {
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

}