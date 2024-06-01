package com.switchwon.paymentimpl.user.out.jpa.repository;

import com.switchwon.consts.Currency;
import com.switchwon.paymentimpl.user.out.jpa.entity.PointEntity;
import com.switchwon.user.domain.User;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PointEntityRepository extends JpaRepository<PointEntity, PointEntity.PointId> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM PointEntity p WHERE p.user = :user and p.currency = :currency")
    Optional<PointEntity> findAndLockById(User user, Currency currency);



}
