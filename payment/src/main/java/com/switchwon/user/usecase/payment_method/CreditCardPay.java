package com.switchwon.user.usecase.payment_method;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.switchwon.consts.ChangeReason;
import com.switchwon.consts.Currency;
import com.switchwon.consts.PayType;
import com.switchwon.shop.domain.Shop;
import com.switchwon.user.adaptor.BalanceChangeHistoryStore;
import com.switchwon.user.adaptor.BalanceChanger;
import com.switchwon.user.adaptor.UserStore;
import com.switchwon.user.domain.Balance;
import com.switchwon.user.domain.BalanceChangeHistory;
import com.switchwon.user.domain.User;
import com.switchwon.user.dto.event.BuyEvent;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;


@RequiredArgsConstructor
public class CreditCardPay implements PaymentMethod{

    private final UserStore userStore;
    private final BalanceChangeHistoryStore balanceChangeHistoryStore;
    private final BalanceChanger balanceChanger;

    @Override
    public boolean able(PayType payType) {
        return payType.equals(PayType.CREDIT_CARD);
    }

    @Override
    public Balance pay(BuyEvent buyEvent) {

        User user = userStore.findById(buyEvent.getUserId());

        Currency matchCurrency = Currency.match(buyEvent.getPaymentMethod());

        Balance currencyBalance = user.getBalances().stream().filter(balance -> balance.getCurrency().equals(matchCurrency)).findFirst().orElseThrow();

        ObjectMapper objectMapper = new ObjectMapper();


        BigDecimal buyAmount = new BigDecimal(buyEvent.getAmount());

        //왼쪽이 크면 1 작으면 -1
        if(currencyBalance.getBalance().compareTo(new BigDecimal(buyEvent.getAmount())) < 0){
            //잔액 부족으로 충전해야함
            BigDecimal insufficient = buyAmount.subtract(currencyBalance.getBalance());

            Card cardInfo = objectMapper.convertValue(buyEvent.getPaymentDetails(), Card.class);
            charge(user, matchCurrency, insufficient, cardInfo);
        }

        return null;
    }

    // 신용카드로 충전하는 로직이 있다고 가정
    private Balance charge(User user, Currency currency, BigDecimal amount, Card card){

        //todo : 신용카드 정보로 충전하는 로직

        BalanceChangeHistory balanceChangeHistory = BalanceChangeHistory.builder()
                .userId(user.getUserId())
                .currency(currency)
                .changeAmount(amount)
                .currentAmount(
                        user.getBalances().stream()
                                .filter(balance -> balance.getCurrency().equals(currency))
                                .findFirst().orElseThrow().getBalance().add(amount)
                )
                .reason(ChangeReason.CHARGE)
                .build();

        balanceChangeHistoryStore.recordHistory(balanceChangeHistory);

        return balanceChanger.adjustBalance(user, currency);
    }

    @ToString
    private class Card {

        private String cardNumber;

        private String date;

        private String cvv;
    }
}