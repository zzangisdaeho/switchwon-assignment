package com.switchwon.user.usecase;

import com.switchwon.BaseAdaptorMockSetting;
import com.switchwon.consts.ChangeReason;
import com.switchwon.user.domain.BalanceChangeHistory;
import com.switchwon.user.domain.ChargeHistory;
import com.switchwon.user.domain.Point;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class UserChargeTest extends BaseAdaptorMockSetting {

    @InjectMocks
    private UserCharge userCharge;

    @Test
    public void chargeTest(){

        String eventId = UUID.randomUUID().toString();
        BigDecimal chargeAmount = BigDecimal.valueOf(1000);

        BigDecimal beforePointBalance = user1PointUSD.getBalance();

        Point afterChargePoint = userCharge.handleCharge(eventId, user1PointUSD, chargeAmount, payDetails, (point, amount, paymentDetails) -> amount);

        BigDecimal afterPointBalance = afterChargePoint.getBalance();

        //충전 전 후 금액의 차이만큼 blanace가 조정되어있어야함
        assertEquals(afterPointBalance.subtract(chargeAmount), beforePointBalance);

        List<BalanceChangeHistory> balanceChangeHistoryList = balanceChangeHistoryMap.values().stream().filter(balanceChangeHistory -> balanceChangeHistory.getEventId().equals(eventId)).collect(Collectors.toList());
        BigDecimal balanceChangeSumValue  = balanceChangeHistoryList.stream().map(BalanceChangeHistory::getChangeAmount).reduce(BigDecimal::add).get();

        ChargeHistory chargeHistory = chargeHistoryMap.get(eventId);

        //chargeAmount만큼 충전 기록이 남아있어야 함
        assertEquals(chargeHistory.getChargeAmount(), chargeAmount);

        //충전 시 balanceChangeHistory에 충전에 의한 잔액 변동 데이터가 있어야함
        BalanceChangeHistory findBalanceChangeHistoryCharge = balanceChangeHistoryList.stream().filter(balanceChangeHistory -> balanceChangeHistory.getReason().equals(ChangeReason.CHARGE)).findFirst().orElseThrow();
        assertNotNull(findBalanceChangeHistoryCharge);

        //충전 시 충전 금액 만큼 잔액 변동 기록이 있어야 함
        assertEquals(findBalanceChangeHistoryCharge.getChangeAmount(), chargeAmount);
    }

}