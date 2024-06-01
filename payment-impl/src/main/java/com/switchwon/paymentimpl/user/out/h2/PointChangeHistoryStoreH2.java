package com.switchwon.paymentimpl.user.out.h2;

import com.switchwon.paymentimpl.user.out.jpa.entity.BalanceChangeHistoryEntity;
import com.switchwon.paymentimpl.user.out.jpa.repository.BalanceChangeHistoryEntityRepository;
import com.switchwon.user.adaptor.PointChangeHistoryStore;
import com.switchwon.user.domain.BalanceChangeHistory;
import com.switchwon.utils.mapper.SwitchwonObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;

@Profile("h2")
@RequiredArgsConstructor
@Service
public class PointChangeHistoryStoreH2 implements PointChangeHistoryStore {

    private final BalanceChangeHistoryEntityRepository balanceChangeHistoryEntityRepository;
    @Override
    @Transactional
    public BalanceChangeHistory recordHistory(BalanceChangeHistory balanceChangeHistory) {

        BalanceChangeHistoryEntity balanceChangeHistoryEntity = new BalanceChangeHistoryEntity();
        try {
            SwitchwonObjectMapper.map(balanceChangeHistoryEntity, balanceChangeHistory);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        return balanceChangeHistoryEntityRepository.save(balanceChangeHistoryEntity);

    }
}
