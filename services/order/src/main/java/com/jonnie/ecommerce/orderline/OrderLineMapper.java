package com.jonnie.ecommerce.orderline;

import com.jonnie.ecommerce.order.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderLineMapper {
    public OrderLine toOrderlineRequest(OrderLineRequest orderLineRequest) {
        return OrderLine.builder()
                .id(orderLineRequest.id())
                .quantity(orderLineRequest.quantity())
                .order(
                        Order.builder()
                                .id(orderLineRequest.id())
                                .build()
                        )
                .productId(orderLineRequest.productId())
                .build();
    }

    public OrderLineResponse fromOrderLine(OrderLine orderLine) {
        return new OrderLineResponse(
                orderLine.getId(),
                orderLine.getQuantity()
        );
    }
}
