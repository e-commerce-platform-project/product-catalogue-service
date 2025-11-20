package ru.ivanov.ecommerceplatformproject.productservice.util;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import ru.ivanov.ecommerceplatformproject.productservice.valueObject.ProductName;

@Converter(autoApply = true)
public class ProductNameConverter implements AttributeConverter<ProductName, String> {
    @Override
    public String convertToDatabaseColumn(ProductName name) {
        return name.name();
    }

    @Override
    public ProductName convertToEntityAttribute(String name) {
        return new ProductName(name);
    }
}
