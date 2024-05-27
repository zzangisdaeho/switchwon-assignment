package com.switchwon.paymentimpl.payment.in;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class PaymentController {

    @GetMapping("/test")
    public String test(){
        return "test";
    }
}
