package ru.ivanov.ecommerceplatformproject.productservice.dto.request;

import java.math.BigDecimal;

public record UpdateProductRequest(
        String name,
        String description,
        String category,
        BigDecimal price
) {
}