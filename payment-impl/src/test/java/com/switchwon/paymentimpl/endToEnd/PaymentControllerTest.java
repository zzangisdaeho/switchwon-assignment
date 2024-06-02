package com.switchwon.paymentimpl.endToEnd;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.switchwon.consts.Currency;
import com.switchwon.paymentimpl.config.InitTestDataConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles({"h2", "test"})
//각 테스트 메소드가 실행된 후에 애플리케이션 컨텍스트 초기화. -> DB초기화됨
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class PaymentControllerTest {


    @LocalServerPort
    private int port;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private InitTestDataConfig initTestDataConfig;

    //Init data 조정
    private final int USDAmount = 500;
    private final int KWRAmount = 100000;
    private final int feePercent = 3;

    @BeforeEach
    public void setup() {
        initTestDataConfig.insertTestData(USDAmount, KWRAmount, feePercent);
    }

    @Test
    public void testGetBalance() throws Exception {
        //given
        String userId = "user1";

        //when, then
        if(userId.equals("user1")){
            MvcResult result = mockMvc.perform(get("/payment/balance/{userId}", userId))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.userId").value(userId))
                    .andExpect(jsonPath("$.balances[0].balance").value(USDAmount))
                    .andExpect(jsonPath("$.balances[0].currency").value("USD"))
                    .andExpect(jsonPath("$.balances[1].balance").value(KWRAmount))
                    .andExpect(jsonPath("$.balances[1].currency").value("KRW"))
                    .andReturn();

//            String responseContent = result.getResponse().getContentAsString();
//            System.out.println("responseContent = " + responseContent);

        }else{
            MvcResult result = mockMvc.perform(get("/payment/balance/{userId}", userId))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.statusCode").value(400))
                    .andExpect(jsonPath("$.message").value("User not found"))
                    .andReturn();

//            String responseContent = result.getResponse().getContentAsString();
//            System.out.println("responseContent = " + responseContent);
        }
    }

    @Test
    public void testEstimatePayment() throws Exception {
//        String estimateRequest = """
//                {
//                    "amount": 150.00,
//                    "currency": "KRW",
//                    "merchantId": "shop1",
//                    "userId": "user1"
//                }
//                """;

        //given
        Currency currency = Currency.KRW;
        BigDecimal amount = currency.decimalRefine(BigDecimal.valueOf(150.00));
        BigDecimal fee = currency.decimalRefine(amount.multiply(BigDecimal.valueOf(feePercent)).divide(BigDecimal.valueOf(100), RoundingMode.FLOOR));
        BigDecimal total = currency.decimalRefine(amount.add(fee));


        ObjectNode estimateRequest = objectMapper.createObjectNode();
        estimateRequest.put("amount", amount);
        estimateRequest.put("currency", currency.name());
        estimateRequest.put("merchantId", "shop1");
        estimateRequest.put("userId", "user1");


        //when, then
        mockMvc.perform(post("/payment/estimate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(estimateRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estimatedTotal").value(total))
                .andExpect(jsonPath("$.fees").value(fee))
                .andExpect(jsonPath("$.currency").value(currency.name()));
    }

    @Test
    public void testApprovePayment() throws Exception {
//        String approvalRequest = """
//                {
//                    "userId": "user1",
//                    "amount": 1500.00,
//                    "currency": "USD",
//                    "merchantId": "shop1",
//                    "paymentMethod": "creditCard",
//                    "paymentDetails": {
//                        "cardNumber": "1234-5678-9123-4567",
//                        "expiryDate": "12/24",
//                        "cvv": "123"
//                    }
//                }
//                """;

        //given
        Currency currency = Currency.USD;
        BigDecimal amount = currency.decimalRefine(BigDecimal.valueOf(1500.00));
        BigDecimal fee = currency.decimalRefine(amount.multiply(BigDecimal.valueOf(feePercent)).divide(BigDecimal.valueOf(100), RoundingMode.FLOOR));
        BigDecimal total = currency.decimalRefine(amount.add(fee));

        ObjectNode paymentDetails = objectMapper.createObjectNode();
        paymentDetails.put("cardNumber", "1234-5678-9123-4567");
        paymentDetails.put("expiryDate", "12/24");
        paymentDetails.put("cvv", "123");

        ObjectNode approvalRequest = objectMapper.createObjectNode();
        approvalRequest.put("userId", "user1");
        approvalRequest.put("amount", amount);
        approvalRequest.put("currency", "USD");
        approvalRequest.put("merchantId", "shop1");
        approvalRequest.put("paymentMethod", "creditCard");
        approvalRequest.set("paymentDetails", paymentDetails);

        //when, then
        mockMvc.perform(post("/payment/approval")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(approvalRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("approved"))
                .andExpect(jsonPath("$.amountTotal").value(total.doubleValue()))
                .andExpect(jsonPath("$.currency").value(currency.name()));
    }
}

