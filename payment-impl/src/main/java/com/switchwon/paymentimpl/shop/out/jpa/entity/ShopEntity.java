package com.switchwon.paymentimpl.shop.out.jpa.entity;

import com.switchwon.shop.domain.Shop;
import com.switchwon.shop.domain.ShopSellHistory;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor
public class ShopEntity extends Shop {

    @Id
    @Override
    public String getMerchantId() {
        return super.getMerchantId();
    }

    @Override
    public BigDecimal getFeePercent() {
        return super.getFeePercent();
    }

    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL)
    @Override
    public List<ShopSellHistoryEntity> getSellHistoryList() {
        return super.getSellHistoryList().stream()
                .map(shopSellHistory -> (ShopSellHistoryEntity)shopSellHistory)
                .collect(Collectors.toList());
    }

}
