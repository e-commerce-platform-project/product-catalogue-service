package ru.ivanov.ecommerceplatformproject.productservice.query;

import java.util.List;
import java.util.UUID;

public record GetAllCartProductsQuery(
        List<UUID> ids
) {}