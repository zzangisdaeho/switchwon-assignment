package com.switchwon.shop.adaptor;

import com.switchwon.shop.domain.ShopSellHistory;

import java.lang.reflect.InvocationTargetException;

public interface ShopSellHistoryStore {
    ShopSellHistory save(ShopSellHistory shopSellHistory);
}
