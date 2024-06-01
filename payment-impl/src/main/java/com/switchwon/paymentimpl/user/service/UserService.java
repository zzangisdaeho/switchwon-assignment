package com.switchwon.paymentimpl.user.service;

import com.switchwon.avro.response.BalanceDto;
import com.switchwon.avro.response.BalanceResponseDto;
import com.switchwon.consts.Currency;
import com.switchwon.user.domain.User;
import com.switchwon.user.usecase.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserInfo userInfo;

    @Transactional
    public BalanceResponseDto getUserInfo(String userId){
        User user = userInfo.getUser(userId);

        List<BalanceDto> list = user.getPoints().stream()
                .map(balance -> {
                    Currency currency = Currency.match(balance.getCurrency().toString());
                    BigDecimal refinedBalance = currency.decimalRefine(balance.getBalance());
                    return new BalanceDto(refinedBalance, balance.getCurrency().toString());
                })
                .collect(Collectors.toList());

        return new BalanceResponseDto(user.getUserId(), list);
    }
}
