package ru.ivanov.ecommerceplatformproject.productservice.valueObject;

import java.util.Objects;
import java.util.UUID;

public class BrandId {

    private final UUID id;

    public BrandId(UUID id) {
        this.id = Objects.requireNonNull(id, "");//todo
    }

    public static BrandId newId() {
        return new BrandId(UUID.randomUUID());
    }

    public static BrandId from(String id) {
        return new BrandId(UUID.fromString(id));
    }


}