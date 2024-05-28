package com.switchwon.paymentimpl.payment.out.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class User {
    @Id
    private String userId;
    private double balance;
    private String currency;

    @OneToMany(mappedBy = "user")
    private List<Payment> payments;
}
