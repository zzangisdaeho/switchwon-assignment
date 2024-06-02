package com.switchwon.paymentimpl.config;

import com.switchwon.consts.Currency;
import com.switchwon.paymentimpl.shop.out.jpa.entity.ShopEntity;
import com.switchwon.paymentimpl.shop.out.jpa.entity.ShopSellHistoryEntity;
import com.switchwon.paymentimpl.shop.out.jpa.repository.ShopEntityRepository;
import com.switchwon.paymentimpl.user.out.jpa.entity.PointEntity;
import com.switchwon.paymentimpl.user.out.jpa.entity.UserEntity;
import com.switchwon.paymentimpl.user.out.jpa.repository.UserEntityRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Component
public class InitTestDataConfig {

    private final UserEntityRepository userEntityRepository;
    private final ShopEntityRepository shopEntityRepository;

    private int USDAmount;

    private int KWRAmount;

    private int feePercent;

    public InitTestDataConfig(UserEntityRepository userEntityRepository, ShopEntityRepository shopEntityRepository) {
        this.userEntityRepository = userEntityRepository;
        this.shopEntityRepository = shopEntityRepository;
    }

    @Transactional
    public void insertTestData(int USDAmount, int KWRAmount, int feePercent) {

        this.USDAmount = USDAmount;
        this.KWRAmount = KWRAmount;
        this.feePercent = feePercent;


        createUserWithBalances("user1");
//        createUserWithBalances("user2");
//        createUserWithBalances("user3");

        createShop("shop1", new BigDecimal(feePercent));
//        createShop("shop2", new BigDecimal(feePercent));
//        createShop("shop3", new BigDecimal(feePercent));
    }

    private void createUserWithBalances(String userId) {
        UserEntity user = new UserEntity();
        user.setUserId(userId);

        PointEntity balance1 = new PointEntity();
        balance1.setCurrency(Currency.USD);
        balance1.setBalance(BigDecimal.valueOf(USDAmount).setScale(2, BigDecimal.ROUND_HALF_UP));
        balance1.setUser(user);

        PointEntity balance2 = new PointEntity();
        balance2.setCurrency(Currency.KRW);
        balance2.setBalance(BigDecimal.valueOf(KWRAmount).setScale(0, BigDecimal.ROUND_HALF_UP));
        balance2.setUser(user);

        user.addPoint(balance1);
        user.addPoint(balance2);

        userEntityRepository.save(user);
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

        shop.addShopSellHistory(history1);
        shop.addShopSellHistory(history2);

        shopEntityRepository.save(shop);
    }
}