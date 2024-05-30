package com.switchwon.paymentimpl.user.out.jpa.repository;

import com.switchwon.paymentimpl.user.out.jpa.entity.ChargeHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChargeHistoryEntityRepository extends JpaRepository<ChargeHistoryEntity, String> {
}
