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
import com.switchwon.user.domain.User;
import com.switchwon.user.usecase.UserCharge;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
public abstract class AbstractPaymentMethod implements PaymentMethod {

    protected final PointStore pointStore;
    protected final PointChangeHistoryStore pointChangeHistoryStore;
    protected final ChargeHistoryStore chargeHistoryStore;
    private final UserCharge userCharge;
    protected final ObjectMapper objectMapper;

    @Override
    public Point getUserBalance(User user, Currency currency) {
        return pointStore.findElseCreate(user, currency);
    }

    @Override
    public Point handleCharge(String eventId, Point point, BigDecimal insufficientAmount, Object paymentDetails) {
        return userCharge.handleCharge(eventId, point, insufficientAmount, paymentDetails, this::executeCharge);
    }

    @Override
    public Point handlePayment(PurchaseEvent purchaseEvent, Point point) {
        BigDecimal totalPay = purchaseEvent.getShop().calculateTotalAmount(purchaseEvent.getAmount(), purchaseEvent.getCurrency());
        BigDecimal payAmount = executePayment(purchaseEvent, point);
        point = pointStore.updateBalance(purchaseEvent.getUser(), purchaseEvent.getCurrency(), totalPay.negate());
        userCharge.recordBalanceChangeHistory(purchaseEvent.getTransactionId(), point, totalPay, ChangeReason.BUY, false);
        return point;
    }

    protected abstract BigDecimal executeCharge(Point point, BigDecimal insufficientAmount, Object paymentDetails);

    protected abstract BigDecimal executePayment(PurchaseEvent purchaseEvent, Point point);

}