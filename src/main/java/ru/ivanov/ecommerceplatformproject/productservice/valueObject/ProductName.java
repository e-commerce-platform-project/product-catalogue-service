package ru.ivanov.ecommerceplatformproject.productservice.valueObject;

public record ProductName(
        String name
) {
    public ProductName {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException();//todo
        }
    }
}