package ru.ivanov.ecommerceplatformproject.productservice.util;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import ru.ivanov.ecommerceplatformproject.productservice.valueObject.ProductId;

@Converter(autoApply = true)
public class ProductIdConverter implements AttributeConverter<ProductId, String> {
    @Override
    public String convertToDatabaseColumn(ProductId id) {
        return id.toString();
    }

    @Override
    public ProductId convertToEntityAttribute(String id) {
        return new ProductId(id);
    }
}
