package com.switchwon.paymentimpl.payment.out.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Payment {
    @Id
    private String paymentId;
    private double amount;
    private String currency;
    private String merchantId;
    private String paymentMethod;
    private String cardNumber;
    private String expiryDate;
    private String cvv;
    private double amountTotal;
    private String status;
    private String timestamp;

    @ManyToOne
    private User user;
}

