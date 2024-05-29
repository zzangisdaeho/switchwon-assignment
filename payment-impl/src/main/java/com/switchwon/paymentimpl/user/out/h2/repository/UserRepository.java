package com.switchwon.paymentimpl.user.out.h2.repository;

import com.switchwon.paymentimpl.user.out.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, String> {
}
