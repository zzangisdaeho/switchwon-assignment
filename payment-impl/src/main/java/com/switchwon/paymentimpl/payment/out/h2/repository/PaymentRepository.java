package com.switchwon.paymentimpl.payment.out.h2.repository;

import com.switchwon.paymentimpl.payment.out.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, String> {
}