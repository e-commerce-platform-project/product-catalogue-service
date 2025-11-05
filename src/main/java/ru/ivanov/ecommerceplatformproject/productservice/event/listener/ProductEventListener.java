package ru.ivanov.ecommerceplatformproject.productservice.event.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.ivanov.ecommerceplatformproject.productservice.event.service.ProductEventService;
import ru.ivanov.ecommerceplatformproject.sharedlibs.event.ProductApprovedEvent;
import ru.ivanov.ecommerceplatformproject.sharedlibs.event.ProductUpdatedEvent;

@Component
@RequiredArgsConstructor
public class ProductEventListener {

    private final ProductEventService productEventService;

    @KafkaListener(topics = "product-approved-event-topic")
    public void handleProductApprovedEvent(@Payload ProductApprovedEvent event) {
        productEventService.createProduct(event);
    }

    @KafkaListener(topics = "product-updated-event-topic")
    public void handleProductUpdatedEvent(@Payload ProductUpdatedEvent event) {
        productEventService.updateProduct(event);
    }
}