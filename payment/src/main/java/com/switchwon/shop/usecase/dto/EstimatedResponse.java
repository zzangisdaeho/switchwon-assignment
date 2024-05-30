package com.switchwon.shop.usecase.dto;

import com.switchwon.consts.Currency;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class EstimatedResponse {

    private BigDecimal total;

    private BigDecimal fees;

    private Currency currency;


}
