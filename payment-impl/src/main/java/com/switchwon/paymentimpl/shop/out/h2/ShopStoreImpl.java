package com.switchwon.paymentimpl.shop.out.h2;

import com.switchwon.error.ShopNotFoundException;
import com.switchwon.paymentimpl.shop.out.jpa.repository.ShopEntityRepository;
import com.switchwon.shop.adaptor.ShopStore;
import com.switchwon.shop.domain.Shop;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("h2")
@Service
@RequiredArgsConstructor
public class ShopStoreImpl implements ShopStore {

    private final ShopEntityRepository shopEntityRepository;

    @Override
    public Shop findById(String merchantId) {
        return shopEntityRepository.findById(merchantId).orElseThrow(ShopNotFoundException::new);
    }
}
