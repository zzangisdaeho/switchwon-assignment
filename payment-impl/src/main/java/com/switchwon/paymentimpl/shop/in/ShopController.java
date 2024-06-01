package com.switchwon.paymentimpl.shop.in;

import com.switchwon.avro.request.EstimateRequestDto;
import com.switchwon.avro.response.EstimateResponseDto;
import com.switchwon.paymentimpl.shop.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("payment")
public class ShopController {

    private final ShopService shopService;

    @PostMapping("/estimate")
    public EstimateResponseDto estimatePayment(@RequestBody EstimateRequestDto estimateRequestDto) {
        return shopService.priceEstimate(estimateRequestDto.getAmount(), estimateRequestDto.getCurrency(), estimateRequestDto.getMerchantId(), estimateRequestDto.getUserId());
    }


}
