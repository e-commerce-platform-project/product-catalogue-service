package ru.ivanov.ecommerceplatformproject.productservice.valueObject;

import jakarta.persistence.Column;
import lombok.Getter;

import java.util.UUID;

@Getter
public class SellerId {

    @Column(name = "value")
    private final UUID id;

    public SellerId(String id) {
        this.id = UUID.fromString(id);
    }

    public SellerId(UUID id) {
        this.id = id;
    }
}