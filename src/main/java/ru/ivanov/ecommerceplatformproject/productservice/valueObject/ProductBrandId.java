package ru.ivanov.ecommerceplatformproject.productservice.valueObject;

import ru.ivanov.ecommerceplatformproject.productservice.model.ProductBrand;

import java.util.Objects;
import java.util.UUID;

public class ProductBrandId {

    private final UUID id;

    public ProductBrandId(UUID id) {
        this.id = Objects.requireNonNull(id, "");//todo
    }

    public static ProductBrandId newId() {
        return new ProductBrandId(UUID.randomUUID());
    }

    public static ProductBrandId from(String id) {
        return new ProductBrandId(UUID.fromString(id));
    }


}