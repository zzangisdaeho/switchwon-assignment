package com.switchwon.paymentimpl.user.out.jpa.repository;

import com.switchwon.paymentimpl.user.out.jpa.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserEntityRepository extends JpaRepository<UserEntity, String> {
}
