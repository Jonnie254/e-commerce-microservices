package com.jonnie.ecommerce.order;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    // method to create an order
    @PostMapping
    public ResponseEntity<Integer> createOrder(
            @RequestBody @Valid OrderRequest orderRequest
    ){
        return ResponseEntity.ok(orderService.createOrder(orderRequest));
    }

    // method to find all orders
    @GetMapping
    public ResponseEntity<List<OrderResponse>> findAllOrders(){
        return ResponseEntity.ok(orderService.findAllOrders());
    }

    // method to find an order by id
    @GetMapping("/{order-id}")
    public ResponseEntity<OrderResponse> findOrderById(
            @PathVariable("order-id") Integer orderId
    ){
        return ResponseEntity.ok(orderService.findOrderById(orderId));
    }
}
