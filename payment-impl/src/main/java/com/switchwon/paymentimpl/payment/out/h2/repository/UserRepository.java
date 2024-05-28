package com.switchwon.paymentimpl.payment.out.h2.repository;

import com.switchwon.paymentimpl.payment.out.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
