package com.switchwon.paymentimpl.test_config.initdata;

import com.switchwon.consts.Currency;
import com.switchwon.paymentimpl.shop.out.jpa.entity.ShopEntity;
import com.switchwon.paymentimpl.shop.out.jpa.entity.ShopSellHistoryEntity;
import com.switchwon.paymentimpl.shop.out.jpa.repository.ShopEntityRepository;
import com.switchwon.paymentimpl.shop.out.jpa.repository.ShopSellHistoryEntityRepository;
import com.switchwon.paymentimpl.user.out.jpa.entity.PointEntity;
import com.switchwon.paymentimpl.user.out.jpa.entity.UserEntity;
import com.switchwon.paymentimpl.user.out.jpa.repository.PointEntityRepository;
import com.switchwon.paymentimpl.user.out.jpa.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Component
@Profile("test")
@RequiredArgsConstructor
public class InitData {

    private final UserEntityRepository userEntityRepository;
    private final PointEntityRepository pointEntityRepository;
    private final ShopEntityRepository shopEntityRepository;
    private final ShopSellHistoryEntityRepository shopSellHistoryEntityRepository;



    @EventListener(ApplicationReadyEvent.class)
    @Order(1)
    @Transactional
    public void UserDataInsert() {
        createUserWithBalances("user1");
        createUserWithBalances("user2");
        createUserWithBalances("user3");

    }

    private void createUserWithBalances(String userId) {
        UserEntity user = new UserEntity();
        user.setUserId(userId);

        PointEntity balance1 = new PointEntity();
        balance1.setCurrency(Currency.USD);
        balance1.setBalance(BigDecimal.valueOf(Math.random() * 1000).setScale(2, BigDecimal.ROUND_HALF_UP));
        balance1.setUser(user);

        PointEntity balance2 = new PointEntity();
        balance2.setCurrency(Currency.KRW);
        balance2.setBalance(BigDecimal.valueOf(Math.random() * 1000000).setScale(0, BigDecimal.ROUND_HALF_UP));
        balance2.setUser(user);

        user.addBalance(balance1);
        user.addBalance(balance2);

        userEntityRepository.save(user);
//        balanceEntityRepository.save(balance1);
//        balanceEntityRepository.save(balance2);

    }

    @EventListener(ApplicationReadyEvent.class)
    @Order(2)
    @Transactional
    public void shopDataInsert() {
        createShop("shop1", new BigDecimal("3.0"));
        createShop("shop2", new BigDecimal("3.0"));
        createShop("shop3", new BigDecimal("3.0"));
    }

    private void createShop(String merchantId, BigDecimal feePercent) {
        ShopEntity shop = new ShopEntity();
        shop.setMerchantId(merchantId);
        shop.setFeePercent(feePercent);

        ShopSellHistoryEntity history1 = ShopSellHistoryEntity.builder()
                .eventId(String.format("%s-%s", merchantId, "event1"))
                .userId("user1")
                .amount(new BigDecimal("100.00"))
                .fees(shop.calculateFee(new BigDecimal("100.00"), Currency.USD))
                .amountTotal(new BigDecimal("100.00").add(shop.calculateFee(new BigDecimal("100.00"), Currency.USD)))
                .shop(shop)
                .build();

        ShopSellHistoryEntity history2 = ShopSellHistoryEntity.builder()
                .eventId(String.format("%s-%s", merchantId, "event2"))
                .userId("user2")
                .amount(new BigDecimal("200.00"))
                .fees(shop.calculateFee(new BigDecimal("200.00"), Currency.USD))
                .amountTotal(new BigDecimal("200.00").add(shop.calculateFee(new BigDecimal("200.00"), Currency.USD)))
                .shop(shop)
                .build();

//        shop.getSellHistoryList().add(history1);
//        shop.getSellHistoryList().add(history2);
        shop.addShopSellHistory(history1);
        shop.addShopSellHistory(history2);

        shopEntityRepository.save(shop);
//        shopSellHistoryEntityRepository.save(history1);
//        shopSellHistoryEntityRepository.save(history2);
    }

    @EventListener(ApplicationReadyEvent.class)
    @Order(5)
    @Transactional
    public void readTest(){
        UserEntity user1 = userEntityRepository.findById("user1").orElseThrow();

        System.out.println("InitData.readTest");
    }

    @EventListener(ApplicationReadyEvent.class)
    @Order(6)
    @Transactional
    public void readTest2(){
        ShopEntity shop1 = shopEntityRepository.findById("shop1").orElseThrow();
        System.out.println("InitData.readTest2");

        ShopSellHistoryEntity history1 = ShopSellHistoryEntity.builder()
                .eventId(String.format("%s-%s", shop1.getMerchantId(), "event3"))
                .userId("user3")
                .amount(new BigDecimal("100.00"))
                .fees(shop1.calculateFee(new BigDecimal("100.00"), Currency.USD))
                .amountTotal(new BigDecimal("100.00").add(shop1.calculateFee(new BigDecimal("100.00"), Currency.USD)))
                .shop(shop1)
                .build();

        shop1.addShopSellHistory(history1);

    }
}
