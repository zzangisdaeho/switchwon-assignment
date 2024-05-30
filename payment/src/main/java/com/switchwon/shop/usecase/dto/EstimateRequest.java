package com.switchwon.shop.usecase.dto;

import com.switchwon.consts.Currency;
import com.switchwon.shop.domain.Shop;
import com.switchwon.user.domain.User;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class EstimateRequest {

    private BigDecimal amount;

    private Currency currency;

    private Shop shop;

    private User user;
}
