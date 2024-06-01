package com.switchwon.paymentimpl.user.out.h2;

import com.switchwon.consts.Currency;
import com.switchwon.error.ChargeHistoryNotFoundException;
import com.switchwon.paymentimpl.user.out.jpa.entity.ChargeHistoryEntity;
import com.switchwon.paymentimpl.user.out.jpa.repository.ChargeHistoryEntityRepository;
import com.switchwon.user.adaptor.ChargeHistoryStore;
import com.switchwon.user.domain.ChargeHistory;
import com.switchwon.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Profile("h2")
@RequiredArgsConstructor
@Service
public class ChargeHistoryStoreH2 implements ChargeHistoryStore {

    private final ChargeHistoryEntityRepository chargeHistoryEntityRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public ChargeHistory progress(String eventId, User user, BigDecimal amount, Currency currency, Object payDetail) {
        ChargeHistoryEntity chargeHistoryEntity = ChargeHistoryEntity.builder()
                .eventId(eventId)
                .user(user)
                .chargeStatus(ChargeHistory.ChargeStatus.PROGRESS)
                .currency(currency)
                .chargeAmount(amount)
                .chargeBy(payDetail)
                .build();

        return chargeHistoryEntityRepository.save(chargeHistoryEntity);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public ChargeHistory updateStatus(String eventId, ChargeHistory.ChargeStatus chargeStatus) {
        ChargeHistoryEntity chargeHistoryEntity = chargeHistoryEntityRepository.findById(eventId).orElseThrow(ChargeHistoryNotFoundException::new);

        chargeHistoryEntity.updateStatus(chargeStatus);

        return chargeHistoryEntity;
    }
}
