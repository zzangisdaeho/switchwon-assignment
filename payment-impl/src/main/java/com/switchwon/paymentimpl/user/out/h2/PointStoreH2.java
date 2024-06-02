package com.switchwon.paymentimpl.user.out.h2;

import com.switchwon.consts.Currency;
import com.switchwon.error.PointNotFoundException;
import com.switchwon.paymentimpl.user.out.jpa.entity.PointEntity;
import com.switchwon.paymentimpl.user.out.jpa.repository.PointEntityRepository;
import com.switchwon.user.adaptor.PointStore;
import com.switchwon.user.domain.Point;
import com.switchwon.user.domain.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.function.Supplier;

@Profile("h2")
@Service
@RequiredArgsConstructor
public class PointStoreH2 implements PointStore {

    private final PointEntityRepository pointEntityRepository;
    private final EntityManager entityManager;

    private Supplier<PointEntity> createNewBalanceType(User user, Currency currency) {
        return () -> {
            PointEntity build = PointEntity.builder()
                    .balance(BigDecimal.ZERO)
                    .currency(currency)
                    .build();
            user.addPoint(build);
            entityManager.lock(build, LockModeType.PESSIMISTIC_READ);
            return build;
        };
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public Point findElseCreate(User user, Currency currency) {
        return pointEntityRepository.findById(new PointEntity.PointId(currency, user.getUserId())).orElseGet(createNewBalanceType(user, currency));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public Point updateBalance(User user, Currency currency, BigDecimal changeAmount) {

        PointEntity findPoint = pointEntityRepository.findAndLockById(user, currency).orElseThrow(PointNotFoundException::new);

        findPoint.changeBalance(changeAmount);

        return findPoint;
    }

}
