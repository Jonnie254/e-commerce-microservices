package com.jonnie.ecommerce.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationProducer {
    private final KafkaTemplate<String, PaymentNotificationRequest> kafkaTemplate;

    public void sendNotification(PaymentNotificationRequest paymentNotificationRequest) {
        log.info("Sending notification to kafka topic: <{}>", paymentNotificationRequest);
        Message<PaymentNotificationRequest> message = MessageBuilder
                .withPayload(paymentNotificationRequest)
                .setHeader("type", "payment-topic")
                .build();
        kafkaTemplate.send(message);
    }
}
