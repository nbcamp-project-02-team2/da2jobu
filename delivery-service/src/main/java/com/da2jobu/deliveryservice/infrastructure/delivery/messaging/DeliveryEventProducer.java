package com.da2jobu.deliveryservice.infrastructure.delivery.messaging;

import com.da2jobu.deliveryservice.application.delivery.event.DeliveryCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeliveryEventProducer {

    private static final String DELIVERY_CREATED_TOPIC = "delivery.created.v1";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishDeliveryCreated(DeliveryCreatedEvent event) {
        kafkaTemplate.send(DELIVERY_CREATED_TOPIC, event.orderId().toString(), event);
        log.info("DeliveryCreatedEvent 발행 완료 - orderId={}, deliveryId={}",
                event.orderId(), event.deliveryId());
    }
}