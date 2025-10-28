package com.example.toss_payments_practice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PaymentWebhookData {
    private String paymentKey;
    private String orderId;
    private String status;
    private Integer totalAmount;
    private String method;
}