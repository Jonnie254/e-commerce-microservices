package com.jonnie.ecommerce.orderline;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderLineService {
    private final OrderLineRepository orderLineRepository;
    private final OrderLineMapper orderLineMapper;
    public Integer saveOrderLine(OrderLineRequest orderLineRequest) {
        var order = orderLineMapper.toOrderlineRequest(orderLineRequest);
        return orderLineRepository.save(order).getId();
    }

    public List<OrderLineResponse> findByOrderId(Integer orderId) {
        return orderLineRepository.findAllByOrderId(orderId)
                .stream()
                .map(orderLineMapper::fromOrderLine)
                .toList();

    }
}
