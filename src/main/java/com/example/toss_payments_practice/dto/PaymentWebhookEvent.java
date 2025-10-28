package com.example.toss_payments_practice.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class PaymentWebhookEvent {
    private String eventType;
    private Instant createdAt;
    private PaymentWebhookData data;
}