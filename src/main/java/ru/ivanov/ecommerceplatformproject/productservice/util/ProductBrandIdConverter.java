package ru.ivanov.ecommerceplatformproject.productservice.util;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import ru.ivanov.ecommerceplatformproject.productservice.valueObject.ProductBrandId;

@Converter(autoApply = true)
public class ProductBrandIdConverter implements AttributeConverter<ProductBrandId, String> {

    @Override
    public String convertToDatabaseColumn(ProductBrandId id) {
        return id.toString();
    }

    @Override
    public ProductBrandId convertToEntityAttribute(String id) {
        return new ProductBrandId(id);
    }
}
