package com.switchwon.paymentimpl.shop.out.entity;

import com.switchwon.shop.domain.Shop;
import com.switchwon.shop.domain.ShopSellHistory;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;
import java.util.stream.Collectors;

@Entity
public class ShopEntity extends Shop {

    @Id
    @Override
    public String getMerchantId() {
        return super.getMerchantId();
    }

    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL, orphanRemoval = true)
    @Override
    public List<ShopSellHistoryEntity> getSellHistoryList() {
        return super.getSellHistoryList().stream()
                .map(shopSellHistory -> (ShopSellHistoryEntity)shopSellHistory)
                .toList();
    }

}
