package ru.ivanov.ecommerceplatformproject.productservice.query;

import org.springframework.data.domain.Pageable;
import ru.ivanov.ecommerceplatformproject.sharedlibs.enums.ProductCategory;

import java.math.BigDecimal;

public record GetAllProductsQuery(
        Pageable pageable,
        ProductCategory category,
        BigDecimal minPrice,
        BigDecimal maxPrice
) implements Query {}