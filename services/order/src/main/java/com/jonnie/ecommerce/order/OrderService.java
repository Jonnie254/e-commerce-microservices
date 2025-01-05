package com.jonnie.ecommerce.order;

import com.jonnie.ecommerce.customer.CustomerClient;
import com.jonnie.ecommerce.exception.BusinessException;
import com.jonnie.ecommerce.kafka.OrderConfirmation;
import com.jonnie.ecommerce.kafka.OrderProducer;
import com.jonnie.ecommerce.orderline.OrderLineRequest;
import com.jonnie.ecommerce.orderline.OrderLineService;
import com.jonnie.ecommerce.payment.PaymentClient;
import com.jonnie.ecommerce.payment.PaymentRequest;
import com.jonnie.ecommerce.product.ProductClient;
import com.jonnie.ecommerce.product.PurchaseRequest;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;
    private final PaymentClient paymentClient;


        @Transactional
        public Integer createOrder(OrderRequest orderRequest) {
            // Check the customer (openfeign)
            var customer = customerClient.findCustomerById(orderRequest.customerId())
                    .orElseThrow(() -> new BusinessException("Cannot create order. Customer not found"));

            // Check the products --> use product microservice
            var purchaseProducts = productClient.purchaseProducts(orderRequest.products());

            // Persist the order first
            var order = orderRepository.save(orderMapper.toOrder(orderRequest));

            // Now, persist the order lines
            for (PurchaseRequest purchaseRequest : orderRequest.products()) {
                // OrderLineRequest must reference the already saved Order (order.getId())
                orderLineService.saveOrderLine(
                        new OrderLineRequest(
                                null,
                                order.getId(),
                                purchaseRequest.productId(),
                                purchaseRequest.quantity()
                        )
                );
            }

            // Start the payment process
            var paymentRequest = new PaymentRequest(
                    orderRequest.amount(),
                    orderRequest.paymentMethod(),
                    order.getId(),
                    order.getReference(),
                    customer
            );
            paymentClient.requestOrderPayment(paymentRequest);

            // Send the order confirmation email --> notification microservice (Kafka)
            orderProducer.SendOrderConfirmation(
                    new OrderConfirmation(
                            orderRequest.reference(),
                            orderRequest.amount(),
                            orderRequest.paymentMethod(),
                            customer,
                            purchaseProducts
                    )
            );

            return order.getId();
        }

    public List<OrderResponse> findAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(orderMapper::fromOrder)
                .toList();
    }

    public OrderResponse findOrderById(Integer orderId) {
        return orderRepository.findById(orderId)
                .map(orderMapper::fromOrder)
                .orElseThrow(() -> new EntityNotFoundException
                        (String.format("Order with id %d not found", orderId)));
    }
}
