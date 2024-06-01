package com.switchwon.paymentimpl.shop.out.h2;

import com.switchwon.paymentimpl.shop.out.jpa.entity.ShopSellHistoryEntity;
import com.switchwon.paymentimpl.shop.out.jpa.repository.ShopSellHistoryEntityRepository;
import com.switchwon.shop.adaptor.ShopSellHistoryStore;
import com.switchwon.shop.domain.ShopSellHistory;
import com.switchwon.utils.mapper.SwitchwonObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;

@Profile("h2")
@Service
@RequiredArgsConstructor
public class ShopSellHistoryStoreImpl implements ShopSellHistoryStore {

    private final ShopSellHistoryEntityRepository shopSellHistoryEntityRepository;


    @Transactional
    @Override
    public ShopSellHistory save(ShopSellHistory shopSellHistory) {

        ShopSellHistoryEntity shopSellHistoryEntity = new ShopSellHistoryEntity();
        try {
            SwitchwonObjectMapper.map(shopSellHistoryEntity, shopSellHistory);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        return shopSellHistoryEntityRepository.save(shopSellHistoryEntity);
    }
}
