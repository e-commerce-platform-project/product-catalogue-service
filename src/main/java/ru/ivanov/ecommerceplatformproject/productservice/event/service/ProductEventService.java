package ru.ivanov.ecommerceplatformproject.productservice.event.service;

import ru.ivanov.ecommerceplatformproject.sharedlibs.event.ProductApprovedEvent;
import ru.ivanov.ecommerceplatformproject.sharedlibs.event.ProductUpdatedEvent;

public interface ProductEventService {
    void createProduct(ProductApprovedEvent event);

    void updateProduct(ProductUpdatedEvent event);
}