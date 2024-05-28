package com.switchwon.user.dto.event;

import lombok.Data;

@Data
public class BuyEvent {

    private String userId;
    private String amount;

    private String merchantId;

    private String paymentMethod;

    private Object paymentDetails;
}
