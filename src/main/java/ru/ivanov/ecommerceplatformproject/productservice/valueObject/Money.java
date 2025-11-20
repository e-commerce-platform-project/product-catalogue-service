package ru.ivanov.ecommerceplatformproject.productservice.valueObject;

import java.math.BigDecimal;

public class Money {

    private final BigDecimal price;

    public Money(BigDecimal price) {
        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException();//todo
        }
        this.price = price;
    }

    @Override
    public String toString() {
        return price.toPlainString();
    }
}
