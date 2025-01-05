package com.jonnie.ecommerce.orderline;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderLineService {
    private final OrderLineRepository orderLineRepository;
    private final OrderLineMapper orderLineMapper;

    @Transactional
    public void saveOrderLine(OrderLineRequest orderLineRequest) {
        var order = orderLineMapper.toOrderlineRequest(orderLineRequest);
        orderLineRepository.save(order);
    }

    public List<OrderLineResponse> findByOrderId(Integer orderId) {
        return orderLineRepository.findAllByOrderId(orderId)
                .stream()
                .map(orderLineMapper::fromOrderLine)
                .toList();

    }
}
