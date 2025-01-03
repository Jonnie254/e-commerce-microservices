package com.jonnie.ecommerce.controller;

import com.jonnie.ecommerce.payment.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
}
