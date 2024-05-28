package com.switchwon.paymentimpl.payment.in;

import com.switchwon.paymentimpl.payment.out.PaymentService;
import com.switchwon.paymentimpl.payment.out.entity.Payment;
import com.switchwon.paymentimpl.payment.out.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("payment")
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/balance/{userId}")
    public User getBalance(@PathVariable String userId) {
        return paymentService.getBalance(userId);
    }

    @PostMapping("/estimate")
    public Payment estimatePayment(@RequestBody Payment payment) {
        return paymentService.estimatePayment(payment);
    }

    @PostMapping("/approval")
    public Payment approvePayment(@RequestBody Payment payment) {
        return paymentService.approvePayment(payment);
    }

}
