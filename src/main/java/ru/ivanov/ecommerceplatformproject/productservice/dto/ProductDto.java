package ru.ivanov.ecommerceplatformproject.productservice.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record ProductDto(
        UUID id,
        UUID sellerId,
        String name,
        String description,
        String category,
        BigDecimal price,
        int stockQuantity,
        int reservedQuantity,
        String mainImageUrl,
        List<String> additionalImageUrls
) {
}