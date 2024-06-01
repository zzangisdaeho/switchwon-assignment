package com.switchwon.paymentimpl.shop.out.jpa.repository;

import com.switchwon.paymentimpl.shop.out.jpa.entity.ShopSellHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopSellHistoryEntityRepository extends JpaRepository<ShopSellHistoryEntity, String> {
}
