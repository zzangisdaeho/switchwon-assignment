package com.switchwon.paymentimpl.user.in;

import com.switchwon.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("payment")
public class PaymentController {

//    private final PaymentService paymentService;

    @GetMapping("/balance/{userId}")
    public User getBalance(@PathVariable String userId) {
        return null;
    }

//    @PostMapping("/estimate")
//    public void estimatePayment(@RequestBody Payment payment) {
//        return paymentService.estimatePayment(payment);
//    }
//
//    @PostMapping("/approval")
//    public Payment approvePayment(@RequestBody Payment payment) {
//        return paymentService.approvePayment(payment);
//    }

}
