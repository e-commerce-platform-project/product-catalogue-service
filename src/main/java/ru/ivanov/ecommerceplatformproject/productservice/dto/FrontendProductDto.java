package ru.ivanov.ecommerceplatformproject.productservice.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record FrontendProductDto(
        UUID id,
        String name,
        String description,
        String category,
        BigDecimal price,
        int availableQuantity,
        String mainImageUrl,
        List<String> additionalImageUrls

) implements Serializable {
}