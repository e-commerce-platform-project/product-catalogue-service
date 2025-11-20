package ru.ivanov.ecommerceplatformproject.productservice.util;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import ru.ivanov.ecommerceplatformproject.productservice.valueObject.ProductDescription;

@Converter(autoApply = true)
public class ProductDescriptionConverter implements AttributeConverter<ProductDescription, String> {
    @Override
    public String convertToDatabaseColumn(ProductDescription description) {
        return description.description();
    }

    @Override
    public ProductDescription convertToEntityAttribute(String description) {
        return new ProductDescription(description);
    }
}