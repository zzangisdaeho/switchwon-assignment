package com.switchwon.user.usecase;

import com.switchwon.BaseAdaptorMockSetting;
import com.switchwon.consts.ChangeReason;
import com.switchwon.consts.Currency;
import com.switchwon.consts.PayType;
import com.switchwon.event.PurchaseEvent;
import com.switchwon.user.domain.BalanceChangeHistory;
import com.switchwon.user.domain.ChargeHistory;
import com.switchwon.user.domain.Point;
import com.switchwon.user.usecase.payment_method.PaymentMethod;
import com.switchwon.user.usecase.payment_method.impl.CreditCardPay;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class UserBuyTest extends BaseAdaptorMockSetting {

    @InjectMocks
    private CreditCardPay creditCardPay;

    @InjectMocks
    private UserBuy userBuy;

    @InjectMocks
    private UserCharge userCharge;

    @BeforeEach
    public void setUp() {
        creditCardPay = new CreditCardPay(pointStore, pointChangeHistoryStore, chargeHistoryStore, userCharge, objectMapper);


        List<PaymentMethod> paymentMethodList = Arrays.asList(creditCardPay);
        userBuy = new UserBuy(paymentMethodList);
    }

    @Test
    public void userBuyTest(){
        BigDecimal buyAmount = BigDecimal.valueOf(1500);
        Currency buyCurrency = Currency.USD;
        PurchaseEvent purchaseEvent = PurchaseEvent.builder()
                .user(user1)
                .amount(buyAmount)
                .currency(buyCurrency)
                .shop(shop1)
                .paymentMethod(PayType.CREDIT_CARD)
                .paymentDetails(payDetails)
                .build();
        String eventId = purchaseEvent.getTransactionId();

        BigDecimal beforePoint = pointStore.findElseCreate(user1, Currency.USD).getBalance();

        Point pointAfterBuy = userBuy.buy(purchaseEvent);

        List<BalanceChangeHistory> balanceChangeHistoryList = balanceChangeHistoryMap.values().stream().filter(balanceChangeHistory -> balanceChangeHistory.getEventId().equals(eventId)).collect(Collectors.toList());
        BigDecimal balanceChangeSumValue  = balanceChangeHistoryList.stream().map(BalanceChangeHistory::getChangeAmount).reduce(BigDecimal::add).get();

        //잔액이 부족하면 충전기록에 남아야함
        BigDecimal totalAmount = shop1.calculateTotalAmount(buyAmount, buyCurrency);
        if(beforePoint.compareTo(totalAmount) < 0){
            ChargeHistory chargeHistory = chargeHistoryMap.get(eventId);
            assertEquals(chargeHistory.getChargeAmount(), totalAmount.subtract(beforePoint));

            //충전 시 balanceChangeHistory에 충전에 의한 잔액 변동 데이터가 있어야함
            BalanceChangeHistory findBalanceChangeHistoryCharge = balanceChangeHistoryList.stream().filter(balanceChangeHistory -> balanceChangeHistory.getReason().equals(ChangeReason.CHARGE)).findFirst().orElseThrow();
            assertNotNull(findBalanceChangeHistoryCharge);

            //충전 시 부족금액만큼 잔액 변동 기록이 있어야 함
            assertEquals(findBalanceChangeHistoryCharge.getChangeAmount(), totalAmount.subtract(beforePoint));
        }

        //결제가 되었기 때문에 잔액 변동 기록에 결제금액만큼 차감기록이 있어야함
        BalanceChangeHistory findBalanceChangeHistoryBuy = balanceChangeHistoryList.stream().filter(balanceChangeHistory -> balanceChangeHistory.getReason().equals(ChangeReason.BUY)).findFirst().orElseThrow();
        assertEquals(findBalanceChangeHistoryBuy.getChangeAmount().negate(), totalAmount);

        //최종 포인트 상태가 결제 후 상태와 같아야 함
        assertEquals(pointAfterBuy.getBalance(), user1PointUSD.getBalance());
    }

    //avro 세팅 차이에 의해 상위 모듈 테스트에선 payDetails를 Map으로 넣는다.
    @Test
    public void objectMapperTest(){
        Card card = objectMapper.convertValue(payDetails, Card.class);

        assertEquals(card.getCardNumber(), ((Map<String, String>)payDetails).get("cardNumber"));
        assertEquals(card.getExpiryDate(), ((Map<String, String>)payDetails).get("expiryDate"));
        assertEquals(card.getCvv(), ((Map<String, String>)payDetails).get("cvv"));
    }

    public static class Card {

        private String cardNumber;

        private String expiryDate;

        private String cvv;

        public Card() {
        }

        public Card(String cardNumber, String expiryDate, String cvv) {
            this.cardNumber = cardNumber;
            this.expiryDate = expiryDate;
            this.cvv = cvv;
        }

        public String getCardNumber() {
            return cardNumber;
        }

        public void setCardNumber(String cardNumber) {
            this.cardNumber = cardNumber;
        }

        public String getExpiryDate() {
            return expiryDate;
        }

        public void setExpiryDate(String expiryDate) {
            this.expiryDate = expiryDate;
        }

        public String getCvv() {
            return cvv;
        }

        public void setCvv(String cvv) {
            this.cvv = cvv;
        }

        @Override
        public String toString() {
            return "Card{" +
                    "cardNumber='" + cardNumber + '\'' +
                    ", expiryDate='" + expiryDate + '\'' +
                    ", cvv='" + cvv + '\'' +
                    '}';
        }
    }

}