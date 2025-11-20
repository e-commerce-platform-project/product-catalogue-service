package ru.ivanov.ecommerceplatformproject.productservice.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.ivanov.ecommerceplatformproject.productservice.util.ProductBrandIdConverter;
import ru.ivanov.ecommerceplatformproject.productservice.valueObject.ProductBrandId;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name ="product_brands")
public class Brand {

    @Id
    @Convert(converter = ProductBrandIdConverter.class)
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    private ProductBrandId id;

    @Column(name = "brand", nullable = false, updatable = false, unique = true)
    private String brand;

    public Brand(String brand) {
        if (brand == null || brand.isBlank()) {
            throw new IllegalArgumentException();//todo
        }
        this.id = ProductBrandId.newId();
        this.brand = brand;
    }
}