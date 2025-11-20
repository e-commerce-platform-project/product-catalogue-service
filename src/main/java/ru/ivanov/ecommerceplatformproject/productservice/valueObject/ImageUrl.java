package ru.ivanov.ecommerceplatformproject.productservice.valueObject;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ImageUrl {
    private String url;

    public ImageUrl(String url) {
        this.url = Objects.requireNonNull(url, "");//todo
    }

    @Override
    public String toString() {
        return url;
    }
}