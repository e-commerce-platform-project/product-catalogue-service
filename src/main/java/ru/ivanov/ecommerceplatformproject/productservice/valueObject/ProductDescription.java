package ru.ivanov.ecommerceplatformproject.productservice.valueObject;

public record ProductDescription(
        String description
) {
    public ProductDescription {
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException();//todo
        }
    }
}