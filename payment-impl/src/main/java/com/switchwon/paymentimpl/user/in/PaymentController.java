package com.switchwon.paymentimpl.user.in;

import com.switchwon.avro.response.BalanceDto;
import com.switchwon.avro.response.BalanceResponseDto;
import com.switchwon.user.domain.User;
import com.switchwon.user.usecase.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("payment")
public class PaymentController {

//    private final PaymentService paymentService;

    private final UserInfo userInfo;

    @GetMapping("/balance/{userId}")
    public BalanceResponseDto getBalance(@PathVariable String userId) {
        User user = userInfo.getUser(userId);

        List<BalanceDto> list = user.getBalances().stream().map(balance -> new BalanceDto(balance.getBalance(), balance.getCurrency().toString())).toList();
        return new BalanceResponseDto(user.getUserId(), list);
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
