package com.jonnie.ecommerce.order;

import com.jonnie.ecommerce.customer.CustomerClient;
import com.jonnie.ecommerce.exception.BusinessException;
import com.jonnie.ecommerce.kafka.OrderConfirmation;
import com.jonnie.ecommerce.kafka.OrderProducer;
import com.jonnie.ecommerce.orderline.OrderLineRequest;
import com.jonnie.ecommerce.orderline.OrderLineService;
import com.jonnie.ecommerce.product.ProductClient;
import com.jonnie.ecommerce.product.PurchaseRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;
    public Integer createOrder(OrderRequest orderRequest) {
        //check the customer (openfeign)
        var customer = customerClient.findCustomerById(orderRequest.customerId())
                .orElseThrow(() -> new BusinessException("Cannot create order. Customer not found"));
        //check the products --> use product microservice
        var purchaseproducts = this.productClient.purchaseProducts(orderRequest.products());
        //persist the order
        var order = this.orderRepository.save(orderMapper.toOrder(orderRequest));
        //persist the orderLines
        for(PurchaseRequest purchaseRequest: orderRequest.products()){
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    )
            );

        }
        //todo start the payment process

        //send the order confirmation email --> notification microservice(kafka)
        orderProducer.SendOrderConfirmation(
                new OrderConfirmation(
                        orderRequest.reference(),
                        orderRequest.amount(),
                        orderRequest.paymentMethod(),
                        customer,
                        purchaseproducts
                )
        );
        return order.getId();
    }
}
