package com.jonnie.ecommerce.order;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    public Integer createOrder(OrderRequest orderRequest) {
        return 1;
    }
}
