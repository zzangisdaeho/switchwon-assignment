package com.switchwon.shop.adaptor;

import com.switchwon.shop.domain.Shop;

public interface ShopStore {
    Shop findById(String merchantId);
}
