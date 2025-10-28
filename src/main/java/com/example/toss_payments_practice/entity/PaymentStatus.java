package com.example.toss_payments_practice.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentStatus {
    READY("결제 준비"),
    IN_PROGRESS("결제 준비 중"),
    DONE("결제 완료"),
    CANCELED("결제 취소");

    private final String description;
}
