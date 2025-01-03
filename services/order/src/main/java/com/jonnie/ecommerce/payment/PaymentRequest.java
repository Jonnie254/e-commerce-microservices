package com.jonnie.ecommerce.payment;

import com.jonnie.ecommerce.customer.CustomerResponse;
import com.jonnie.ecommerce.order.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        CustomerResponse customer
) {
}
