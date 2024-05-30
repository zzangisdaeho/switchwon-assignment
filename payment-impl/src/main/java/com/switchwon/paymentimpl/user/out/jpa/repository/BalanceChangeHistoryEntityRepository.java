package com.switchwon.paymentimpl.user.out.jpa.repository;

import com.switchwon.paymentimpl.user.out.jpa.entity.BalanceChangeHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BalanceChangeHistoryEntityRepository extends JpaRepository<BalanceChangeHistoryEntity, String> {
}
