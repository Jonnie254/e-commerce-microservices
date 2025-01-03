package com.jonnie.ecommerce.controller;

import com.jonnie.ecommerce.payment.Payment;
import com.jonnie.ecommerce.payment.PaymentRequest;
import org.springframework.stereotype.Service;

@Service
public class PaymentMapper {
    public Payment toPayment(PaymentRequest paymentRequest) {
        return Payment.builder()
                .id(paymentRequest.id())
                .orderId(paymentRequest.orderId())
                .paymentMethod(paymentRequest.paymentMethod())
                .amount(paymentRequest.amount())
                .build();
    }
}
