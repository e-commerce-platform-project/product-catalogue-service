package ru.ivanov.ecommerceplatformproject.productservice.event.listener;

import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.ivanov.ecommerceplatformproject.productservice.command.CreateProductCommand;
import ru.ivanov.ecommerceplatformproject.productservice.event.service.ProductEventService;
import ru.ivanov.ecommerceplatformproject.sharedlibs.event.ProductApprovedEvent;
import ru.ivanov.ecommerceplatformproject.sharedlibs.event.ProductUpdatedEvent;

@Component
@RequiredArgsConstructor
public class ProductEventListener {

    private final CommandGateway commandGateway;

    @KafkaListener(topics = "product-approved-event-topic")
    public void handleProductApprovedEvent(@Payload ProductApprovedEvent event) {
        commandGateway.send(new CreateProductCommand(
                event.id(),
                event.sellerId(),
                event.name(),
                event.description(),
                event.category(),
                event.brand(),
                event.price(),
                event.mainImageUrl(),
                event.additionalImageUrls()
        ));
    }

    @KafkaListener(topics = "product-updated-event-topic")
    public void handleProductUpdatedEvent(@Payload ProductUpdatedEvent event) {

    }
}