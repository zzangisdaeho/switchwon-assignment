package com.switchwon.paymentimpl.shop.out.jpa.repository;

import com.switchwon.paymentimpl.shop.out.jpa.entity.ShopEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopEntityRepository extends JpaRepository<ShopEntity, String> {
}
