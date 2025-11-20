package ru.ivanov.ecommerceplatformproject.productservice.util;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import ru.ivanov.ecommerceplatformproject.productservice.valueObject.BrandId;

@Converter(autoApply = true)
public class ProductBrandIdConverter implements AttributeConverter<BrandId, String> {

    @Override
    public String convertToDatabaseColumn(BrandId id) {
        return id.toString();
    }

    @Override
    public BrandId convertToEntityAttribute(String id) {
        return new BrandId(id);
    }
}
