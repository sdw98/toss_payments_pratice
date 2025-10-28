package com.example.toss_payments_practice.controller;

import com.example.toss_payments_practice.config.TossPaymentConfig;
import com.example.toss_payments_practice.dto.PaymentRequest;
import com.example.toss_payments_practice.entity.Payment;
import com.example.toss_payments_practice.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PaymentController {

    private final PaymentService paymentService;
    private final TossPaymentConfig tossPaymentConfig;

    @GetMapping("/")
    public String productPage(Model model) {
        model.addAttribute("paymentRequest", new PaymentRequest());
        return "product";
    }

    @PostMapping("/checkout")
    public String checkout(
            @Valid @ModelAttribute PaymentRequest request,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            return "product";
        }

        Payment payment = paymentService.preparePayment(request);

        model.addAttribute("payment", payment);
        model.addAttribute("clientKey", tossPaymentConfig.getClientKey());

        return "checkout";
    }

    @GetMapping("/success")
    public String paymentSuccess(
            @RequestParam String paymentKey,
            @RequestParam String orderId,
            @RequestParam Integer amount,
            Model model
    ) {
        try {
            Payment payment = paymentService.confirmPayment(paymentKey, orderId, amount);
            model.addAttribute("payment", payment);
            return "success";
        } catch (Exception e) {
            log.error("결제 승인 실패", e);
            return "redirect:/fail";
        }
    }

    @GetMapping("/fail")
    public String paymentFail(@RequestParam(required = false) String message, Model model) {
        model.addAttribute("message", message != null ? message : "결제 처리 중 오류가 발생했습니다.");
        return "fail";
    }
}