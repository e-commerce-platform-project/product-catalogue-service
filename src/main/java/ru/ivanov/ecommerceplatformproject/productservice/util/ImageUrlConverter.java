package ru.ivanov.ecommerceplatformproject.productservice.util;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import ru.ivanov.ecommerceplatformproject.productservice.valueObject.ImageUrl;

@Converter(autoApply = true)
public class ImageUrlConverter implements AttributeConverter<ImageUrl, String> {
    @Override
    public String convertToDatabaseColumn(ImageUrl url) {
        return url.toString();
    }

    @Override
    public ImageUrl convertToEntityAttribute(String url) {
        return new ImageUrl(url);
    }
}
