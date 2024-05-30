package com.switchwon.paymentimpl.user.out.jpa.repository;

import com.switchwon.paymentimpl.user.out.jpa.entity.BalanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BalanceEntityRepository extends JpaRepository<BalanceEntity, BalanceEntity.BalanceId> {
}
