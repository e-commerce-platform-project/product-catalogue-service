package ru.ivanov.ecommerceplatformproject.productservice.valueObject;

import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Embeddable
@NoArgsConstructor
public class ProductId {
    private UUID value;

    public ProductId(String value) {
        this.value = UUID.fromString(value);
    }

    public ProductId(UUID value) {
        this.value = value;
    }
}