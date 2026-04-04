package com.da2jobu.productservice.infrastructure.event.consumer;

import com.da2jobu.productservice.application.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderCancelledEventListener {

    private final ProductService productService;

    @KafkaListener(topics = "order.cancelled.v1", groupId = "product-service")
    public void consume(OrderCancelledEvent event) {
        log.info("OrderCancelledEvent 수신 - orderId={}, productId={}, quantity={}",
                event.orderId(), event.productId(), event.quantity());

        try {
            productService.restoreStock(event.productId(), event.quantity());
            log.info("OrderCancelledEvent 처리 완료 - 재고 복구: productId={}, quantity={}",
                    event.productId(), event.quantity());
        } catch (Exception e) {
            log.error("OrderCancelledEvent 처리 실패 - orderId={}", event.orderId(), e);
            throw e;
        }
    }
}
