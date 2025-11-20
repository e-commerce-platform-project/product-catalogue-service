package ru.ivanov.ecommerceplatformproject.productservice.query;

import java.util.UUID;

public record GetProductDetailQuery(
        UUID id
) implements Query {}