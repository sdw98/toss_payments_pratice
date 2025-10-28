package com.example.toss_payments_practice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String orderId;

    @Column
    private String paymentKey;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private String orderName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private PaymentStatus status = PaymentStatus.READY;

    @Column
    private String method;

    @Column
    private LocalDateTime approvedAt;

    @Column
    private String receiptUrl;

    public static Payment create(String orderId, BigDecimal amount, String orderName) {
        return Payment.builder()
                .orderId(orderId)
                .amount(amount)
                .orderName(orderName)
                .status(PaymentStatus.READY)
                .build();
    }

    public void approve(String paymentKey, String method, String receiptUrl) {
        this.paymentKey = paymentKey;
        this.method = method;
        this.status = PaymentStatus.DONE;
        this.approvedAt = LocalDateTime.now();
        this.receiptUrl = receiptUrl;
    }

    public void cancel(String reason) {
        this.status = PaymentStatus.CANCELED;
    }
}