package ru.ivanov.ecommerceplatformproject.productservice.query;

public interface Query {

    interface Handler<Q extends Query, R> {
        R handle(Q query);
    }
}
