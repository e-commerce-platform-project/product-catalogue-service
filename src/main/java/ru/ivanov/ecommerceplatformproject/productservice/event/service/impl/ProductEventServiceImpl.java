package ru.ivanov.ecommerceplatformproject.productservice.event.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.ivanov.ecommerceplatformproject.productservice.event.service.ProductEventService;
import ru.ivanov.ecommerceplatformproject.productservice.mapper.ProductMapper;
import ru.ivanov.ecommerceplatformproject.productservice.model.Product;
import ru.ivanov.ecommerceplatformproject.productservice.repository.ProductRepository;
import ru.ivanov.ecommerceplatformproject.sharedlibs.event.ProductApprovedEvent;
import ru.ivanov.ecommerceplatformproject.sharedlibs.event.ProductUpdatedEvent;
import ru.ivanov.ecommerceplatformproject.sharedlibs.exception.ResourceNotFoundException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductEventServiceImpl implements ProductEventService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    @Transactional
    public void createProduct(ProductApprovedEvent event) {
        if (productRepository.existsById(event.productId())) {
            log.warn("Событие ProductApprovedEvent для товара с id '{}' уже обработано", event.productId());
            return;
        }

        Product product = productMapper.toEntity(event);
        productRepository.save(product);
    }

    @Override
    @Transactional
    public void updateProduct(ProductUpdatedEvent event) {
        Product product = productRepository.findById(event.productId())
                .orElseThrow(() -> new ResourceNotFoundException("Товар не найден"));//todo

        if (event.name() != null && !event.name().isBlank()) {
            product.setName(event.name());
        }

        if (event.description() != null && !event.description().isBlank()) {
            product.setDescription(event.description());
        }

        if (event.category() != null && !event.category().isBlank()) {
            // logic
        }

//        if (event.brand() != null && !event.brand().isBlank()) {
//            product.setBrand(event.brand());
//        }

        if (event.price() != null) {
            product.setPrice(event.price());
        }

        if (event.mainImageUrl() != null && !event.mainImageUrl().isBlank()) {
            product.setMainImageUrl(event.mainImageUrl());
        }

        if (event.additionalImageUrls() != null) {
            product.setAdditionalImageUrls(event.additionalImageUrls());
        }

        productRepository.save(product);
    }
}