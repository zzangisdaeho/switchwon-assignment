package com.switchwon;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.switchwon.consts.Currency;
import com.switchwon.error.ChargeHistoryNotFoundException;
import com.switchwon.error.PointNotFoundException;
import com.switchwon.error.ShopNotFoundException;
import com.switchwon.error.UserNotFoundException;
import com.switchwon.shop.adaptor.ShopSellHistoryStore;
import com.switchwon.shop.adaptor.ShopStore;
import com.switchwon.shop.domain.Shop;
import com.switchwon.shop.domain.ShopSellHistory;
import com.switchwon.user.adaptor.ChargeHistoryStore;
import com.switchwon.user.adaptor.PointChangeHistoryStore;
import com.switchwon.user.adaptor.PointStore;
import com.switchwon.user.adaptor.UserStore;
import com.switchwon.user.domain.BalanceChangeHistory;
import com.switchwon.user.domain.ChargeHistory;
import com.switchwon.user.domain.Point;
import com.switchwon.user.domain.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public abstract class BaseAdaptorMockSetting {

    @Mock
    protected ShopSellHistoryStore shopSellHistoryStore;

    @Mock
    protected ShopStore shopStore;

    @Mock
    protected ChargeHistoryStore chargeHistoryStore;

    @Mock
    protected PointChangeHistoryStore pointChangeHistoryStore;

    @Mock
    protected PointStore pointStore;

    @Mock
    protected UserStore userStore;

    protected Map<String, ChargeHistory> chargeHistoryMap;
    protected Map<UserCurrencyKey, Point> pointMap;
    protected Map<String, User> userMap;
    protected Map<String, Shop> shopMap;
    protected Map<String, ShopSellHistory> shopSellHistoryMap;
    protected Map<Long, BalanceChangeHistory> balanceChangeHistoryMap;

    public record UserCurrencyKey(User user, Currency currency) {

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            UserCurrencyKey that = (UserCurrencyKey) o;
            return Objects.equals(user.getUserId(), that.user.getUserId()) &&
                    currency == that.currency;
        }

    }

    protected static User user1;
    protected static Shop shop1;
    protected static Point user1PointUSD;
//    protected static Point user1PointKWR;

    protected static Object payDetails;

    protected static ObjectMapper objectMapper;

    @BeforeAll
    public static void setUpDomainObjects() {
        System.out.println("setUpDomainObjects");

        user1 = User.builder()
                .userId("user1")
                .build();

        shop1 = Shop.builder()
                .merchantId("shop1")
                .feePercent(BigDecimal.valueOf(3.0))
                .build();

        user1PointUSD = Point.builder()
                .user(user1)
                .currency(Currency.USD)
                .balance(BigDecimal.valueOf(100))
                .build();

        user1.addPoint(user1PointUSD);
//        user1PointKWR = Point.builder()
//                .user(user1)
//                .currency(Currency.KRW)
//                .balance(BigDecimal.valueOf(10000))
//                .build();

//        user1.addPoint(user1PointKWR);

        objectMapper = new ObjectMapper();
        Map<String, String> payDetailMap = new HashMap<>();
        payDetailMap.put("cardNumber", "1234-5678-9123-4567");
        payDetailMap.put("expiryDate", "12/24");
        payDetailMap.put("cvv", "123");
        payDetails = payDetailMap;
    }

    @BeforeEach
    public void setUpMap(){
        System.out.println("setUpMap");
        chargeHistoryMap = new HashMap<>();
        pointMap = new HashMap<>();
        userMap = new HashMap<>();
        shopMap = new HashMap<>();
        shopSellHistoryMap = new HashMap<>();
        balanceChangeHistoryMap = new HashMap<>();

        userMap.put(user1.getUserId(), user1);
        shopMap.put(shop1.getMerchantId(), shop1);
        pointMap.put(new UserCurrencyKey(user1, Currency.USD), user1PointUSD);
//        pointMap.put(new UserCurrencyKey(user1, Currency.KRW), user1PointKWR);
    }

    @BeforeEach
    public void setUpMock() {
        System.out.println("setUpMock");
        //ShopSellHistoryStore stubbing
        Mockito.when(shopSellHistoryStore.save(Mockito.any(ShopSellHistory.class)))
                .thenAnswer(invocationOnMock -> {
                    ShopSellHistory shopSellHistory = invocationOnMock.getArgument(0);
                    shopSellHistoryMap.put(shopSellHistory.getEventId(), shopSellHistory);
                    return shopSellHistory;
                });

        //ShopStore stubbing
        Mockito.when(shopStore.findById(Mockito.anyString()))
                .thenAnswer(invocationOnMock -> {
                    String merchantId = invocationOnMock.getArgument(0);
                    Shop shop = shopMap.get(merchantId);
                    if (shop == null) {
                        throw new ShopNotFoundException();
                    }
                    return shop;
                });

        //ChargeHistoryStore stubbing
        Mockito.when(chargeHistoryStore.progress(Mockito.anyString(), Mockito.any(User.class),
                        Mockito.any(BigDecimal.class), Mockito.any(Currency.class), Mockito.any()))
                .thenAnswer(invocationOnMock -> {
                    String eventId = invocationOnMock.getArgument(0);
                    User user = invocationOnMock.getArgument(1);
                    BigDecimal amount = invocationOnMock.getArgument(2);
                    Currency currency = invocationOnMock.getArgument(3);
                    Object payDetail = invocationOnMock.getArgument(4);

                    ChargeHistory chargeHistory = ChargeHistory.builder()
                            .eventId(eventId)
                            .user(user)
                            .chargeAmount(amount)
                            .currency(currency)
                            .chargeBy(payDetail)
                            .chargeStatus(ChargeHistory.ChargeStatus.PROGRESS)
                            .eventTime(ZonedDateTime.now())
                            .build();

                    chargeHistoryMap.put(eventId, chargeHistory);
                    return chargeHistory;
                });

        Mockito.when(chargeHistoryStore.updateStatus(Mockito.anyString(), Mockito.any(ChargeHistory.ChargeStatus.class)))
                .thenAnswer(invocation -> {
                    String eventId = invocation.getArgument(0);
                    ChargeHistory.ChargeStatus chargeStatus = invocation.getArgument(1);

                    ChargeHistory chargeHistory = chargeHistoryMap.get(eventId);
                    if (chargeHistory != null) {
                        chargeHistory.setChargeStatus(chargeStatus);
                    }else{
                        throw new ChargeHistoryNotFoundException();
                    }
                    return chargeHistory;
                });

        // PointChangeHistoryStore stubbing
        AtomicReference<Long> balanceChangeNo = new AtomicReference<>(1L);
        Mockito.when(pointChangeHistoryStore.recordHistory(Mockito.any(BalanceChangeHistory.class)))
                .thenAnswer(invocation -> {
                    BalanceChangeHistory balanceChangeHistory = invocation.getArgument(0);
                    balanceChangeHistoryMap.put(balanceChangeNo.getAndSet(balanceChangeNo.get() + 1), balanceChangeHistory);
                    return balanceChangeHistory;
                });

        // PointStore stubbing
        Mockito.when(pointStore.findElseCreate(Mockito.any(User.class), Mockito.any(Currency.class)))
                .thenAnswer(invocation -> {
                    User user = invocation.getArgument(0);
                    Currency currency = invocation.getArgument(1);
                    UserCurrencyKey key = new UserCurrencyKey(user, currency);

                    Point point = pointMap.get(key);
                    if (point == null) {
                        point = Point.builder()
                                .user(user)
                                .currency(currency)
                                .balance(BigDecimal.ZERO)
                                .build();
                        pointMap.put(key, point);
                        user.addPoint(point);
                    }
                    return point;
                });

        Mockito.when(pointStore.updateBalance(Mockito.any(User.class), Mockito.any(Currency.class), Mockito.any(BigDecimal.class)))
                .thenAnswer(invocation -> {
                    User user = invocation.getArgument(0);
                    Currency currency = invocation.getArgument(1);
                    BigDecimal changeAmount = invocation.getArgument(2);
                    UserCurrencyKey key = new UserCurrencyKey(user, currency);

                    Point point = pointMap.get(key);
                    if (point == null) {
                        throw new PointNotFoundException("Point not found");
                    }
                    point.changeBalance(changeAmount);
                    return point;
                });

        // UserStore stubbing
        Mockito.when(userStore.findById(Mockito.anyString()))
                .thenAnswer(invocation -> {
                    String userId = invocation.getArgument(0);
                    User user = userMap.get(userId);
                    if(user == null){
                        throw new UserNotFoundException("user not exist");
                    }

                    return user;
                });
    }

}
