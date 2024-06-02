package com.switchwon;

import com.switchwon.consts.ChangeReason;
import com.switchwon.consts.Currency;
import com.switchwon.error.ChargeHistoryNotFoundException;
import com.switchwon.error.PointNotFoundException;
import com.switchwon.error.ShopNotFoundException;
import com.switchwon.error.UserNotFoundException;
import com.switchwon.shop.domain.Shop;
import com.switchwon.shop.domain.ShopSellHistory;
import com.switchwon.user.domain.BalanceChangeHistory;
import com.switchwon.user.domain.ChargeHistory;
import com.switchwon.user.domain.Point;
import com.switchwon.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class BaseAdaptorTest extends BaseAdaptorMockSetting {

    @Test
    @DisplayName("ShopSellHistoryStore Mock stubbing test")
    public void shopSellHistoryStoreMockTest(){
        // Arrange
        ShopSellHistory shopSellHistory = ShopSellHistory.builder()
                .eventId(UUID.randomUUID().toString())
                .shop(Shop.builder()
                        .merchantId("shop1")
                        .feePercent(BigDecimal.valueOf(0.03))
                        .build())
                .userId("user1")
                .fees(BigDecimal.ONE)
                .amount(BigDecimal.valueOf(9))
                .amountTotal(BigDecimal.TEN)
                .eventTime(ZonedDateTime.now())
                .build();

        // Act
        ShopSellHistory savedHistory = shopSellHistoryStore.save(shopSellHistory);

        // Assert
        assertNotNull(savedHistory, "must not be null");
        assertEquals(shopSellHistory, savedHistory, "Returned ShopSellHistory should match the input");
    }

    @Test
    @DisplayName("ShopStore Mock stubbing test")
    public void shopStoreMockTest(){

        //Act
        Shop findShop = shopStore.findById(shop1.getMerchantId());

        //Assert
        assertEquals(findShop.getMerchantId(), shop1.getMerchantId(), "Merchant ID should match");
        assertEquals(BigDecimal.valueOf(3.0), findShop.getFeePercent(), "Fee percent should match");
        assertTrue(findShop.getSellHistoryList().isEmpty(), "sell history list initialize");

        //Arrange
        String notExistMerchantId = "notExistMerchantId";

        //Assert
        assertThrows(ShopNotFoundException.class, () -> shopStore.findById(notExistMerchantId), "ShopNotFoundException should be thrown for non-existent merchantId");
    }

    @Test
    @DisplayName("ChargeHistoryStore Mock stubbing test")
    public void chargeHistoryStoreMockTest(){

        String eventId = UUID.randomUUID().toString();

        //Act
        ChargeHistory progressChargeHistory = chargeHistoryStore.progress(eventId, user1, BigDecimal.TEN, Currency.USD, payDetails);

        //Assert
        assertEquals(progressChargeHistory.getChargeStatus(), ChargeHistory.ChargeStatus.PROGRESS, "must be Progess Status");
        assertEquals(progressChargeHistory.getEventId(), eventId, "eventId must be equal");

        //Arrange
        ChargeHistory.ChargeStatus updateTargetStatus = ChargeHistory.ChargeStatus.FINISH;

        //Act
        ChargeHistory updatedChargeHistory = chargeHistoryStore.updateStatus(eventId, updateTargetStatus);

        //Assert
        assertEquals(updatedChargeHistory.getChargeStatus(), updateTargetStatus, "Status must be updated as given");
        assertEquals(progressChargeHistory.getEventId(), eventId, "eventId must be equal");

        assertThrows(ChargeHistoryNotFoundException.class, () -> chargeHistoryStore.updateStatus("notExistEventId", ChargeHistory.ChargeStatus.FINISH), "ChargeHistoryNotFoundException should be thrown for non-existent eventId");
    }

    @Test
    @DisplayName("PointChangeHistoryStore Mock stubbing test")
    public void pointChangeHistoryStoreMockTest() {

        // Arrange
        BalanceChangeHistory balanceChangeHistory = BalanceChangeHistory.builder()
                .changeNo(1L)
                .eventId("event123")
                .user(user1)
                .currency(Currency.USD)
                .beforeChangeAmount(BigDecimal.valueOf(100))
                .changeAmount(BigDecimal.valueOf(10))
                .afterChangeAmount(BigDecimal.valueOf(110))
                .reason(ChangeReason.CHARGE)
                .eventTime(ZonedDateTime.now())
                .build();

        // Act
        BalanceChangeHistory recordedHistory = pointChangeHistoryStore.recordHistory(balanceChangeHistory);

        // Assert
        assertNotNull(recordedHistory, "Recorded BalanceChangeHistory should not be null");
        assertEquals(balanceChangeHistory, recordedHistory, "Returned BalanceChangeHistory should match the input");
    }

    @Test
    @DisplayName("PointStore Mock stubbing test")
    public void pointStoreMockTest() {
        // Arrange
        Currency currency = Currency.KRW;

        // Assert
        assertThrows(PointNotFoundException.class, () -> pointStore.updateBalance(user1, Currency.KRW, BigDecimal.TEN), "PointNotFoundException should be thrown for non-existent point");

        // Act
        Point point = pointStore.findElseCreate(user1, currency);

        // Assert
        assertNotNull(point, "Point should not be null");
        assertEquals(user1, point.getUser(), "User should match");
        assertEquals(currency, point.getCurrency(), "Currency should match");
        assertEquals(BigDecimal.ZERO, point.getBalance(), "Initial balance should be zero");
        assertTrue(user1.getPoints().contains(point), "User's points should contain the created point");

        // Arrange
        BigDecimal initialAmount = BigDecimal.valueOf(100);
        BigDecimal changeAmount = BigDecimal.valueOf(50);

        point.changeBalance(initialAmount);

        // Act
        Point updatedPoint = pointStore.updateBalance(user1, currency, changeAmount);

        // Assert
        assertNotNull(updatedPoint, "Updated point should not be null");
        assertEquals(initialAmount.add(changeAmount), updatedPoint.getBalance(), "Balance should be updated correctly");


    }

    @Test
    @DisplayName("UserStore Mock stubbing test")
    public void userStoreMockTest() {
        // Act
        User findUser = userStore.findById(user1.getUserId());

        // Assert
        assertNotNull(findUser, "User should not be null");
        assertEquals(user1.getUserId(), findUser.getUserId(), "User ID should match");

        //Arrange
        String userId2 = "userNotExist";

        // Assert
        assertThrows(UserNotFoundException.class, () -> userStore.findById(userId2), "UserNotFoundException should be thrown for non-existent userId");

    }
}
