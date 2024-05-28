package com.switchwon.paymentimpl.payment.in;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("payment")
public class PaymentController {

    @GetMapping("/test")
    public String test(){
        return "test";
    }

    @GetMapping("/test2")
    public Object test2(){
        return "test";
    }

    @GetMapping("/balance")
    public String getBalance(){
        return "test";
    }

    @GetMapping("/estimate")
    public String estimate(){
        return "test";
    }

    @PostMapping("/approval")
    public String approval(){
        return "test";
    }

}
