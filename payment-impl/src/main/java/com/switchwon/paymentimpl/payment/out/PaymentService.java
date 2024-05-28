package com.switchwon.paymentimpl.payment.out;

import com.switchwon.paymentimpl.payment.out.entity.Payment;
import com.switchwon.paymentimpl.payment.out.entity.User;
import com.switchwon.paymentimpl.payment.out.h2.repository.PaymentRepository;
import com.switchwon.paymentimpl.payment.out.h2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final UserRepository userRepository;

    private final PaymentRepository paymentRepository;

    public User getBalance(String userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public Payment estimatePayment(Payment payment) {
        double fees = payment.getAmount() * 0.03;
        double estimatedTotal = payment.getAmount() + fees;
        payment.setAmountTotal(estimatedTotal);
        payment.setStatus("estimated");
        return payment;
    }

    @TransactionalEventListener
    public Payment approvePayment(Payment payment) {
        Optional<User> optionalUser = userRepository.findById(payment.getUser().getUserId());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getBalance() < payment.getAmount()) {
                double additionalAmount = payment.getAmount() - user.getBalance();
                // Implement credit card charge logic here
                user.setBalance(0);
            } else {
                user.setBalance(user.getBalance() - payment.getAmount());
            }
            payment.setStatus("approved");
            payment.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
            paymentRepository.save(payment);
            userRepository.save(user);
        }
        return payment;
    }
}

