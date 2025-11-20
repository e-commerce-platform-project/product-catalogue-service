package ru.ivanov.ecommerceplatformproject.productservice.query.handler;

import ru.ivanov.ecommerceplatformproject.productservice.query.GetProductDetailQuery;
import ru.ivanov.ecommerceplatformproject.productservice.query.Query;
import ru.ivanov.ecommerceplatformproject.productservice.view.ProductDetailView;

public class GetProductDetailQueryHandler implements Query.Handler<GetProductDetailQuery, ProductDetailView> {
    @Override
    public ProductDetailView handle(GetProductDetailQuery query) {
        return null;
    }
}
