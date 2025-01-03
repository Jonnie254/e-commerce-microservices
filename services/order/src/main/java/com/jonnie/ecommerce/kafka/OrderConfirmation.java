package com.jonnie.ecommerce.kafka;

import com.jonnie.ecommerce.customer.CustomerResponse;
import com.jonnie.ecommerce.order.PaymentMethod;
import com.jonnie.ecommerce.product.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> products
) {
}
