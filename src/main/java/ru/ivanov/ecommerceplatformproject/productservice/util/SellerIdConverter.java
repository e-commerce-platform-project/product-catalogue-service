package ru.ivanov.ecommerceplatformproject.productservice.util;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import ru.ivanov.ecommerceplatformproject.productservice.valueObject.SellerId;

@Converter(autoApply = true)
public class SellerIdConverter implements AttributeConverter<SellerId, String> {

    @Override
    public String convertToDatabaseColumn(SellerId id) {
        return id.toString();
    }

    @Override
    public SellerId convertToEntityAttribute(String id) {
        return new SellerId(id);
    }
}