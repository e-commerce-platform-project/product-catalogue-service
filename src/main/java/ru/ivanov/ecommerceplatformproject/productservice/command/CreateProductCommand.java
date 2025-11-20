package ru.ivanov.ecommerceplatformproject.productservice.command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;
import ru.ivanov.ecommerceplatformproject.sharedlibs.enums.ProductCategory;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record CreateProductCommand(
        @TargetAggregateIdentifier
        UUID id,
        UUID sellerId,
        String name,
        String description,
        ProductCategory category,
        String brand,
        BigDecimal price,
        String mainImageUrl,
        List<String> additionalImageUrls
) implements Command {}