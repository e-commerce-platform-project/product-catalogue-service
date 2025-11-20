package ru.ivanov.ecommerceplatformproject.productservice.query;

public interface Query<R extends Query.Result> {
    interface Result{}

    interface Handler<Q extends Query<R>, R extends Query.Result> {
        R handle(Q query);
    }
}
