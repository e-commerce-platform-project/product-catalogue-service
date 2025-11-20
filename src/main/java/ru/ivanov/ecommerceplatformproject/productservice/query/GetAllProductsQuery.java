package ru.ivanov.ecommerceplatformproject.productservice.query;

import ru.ivanov.ecommerceplatformproject.productservice.dto.response.PagedResponse;
import ru.ivanov.ecommerceplatformproject.productservice.query.model.ProductView;

public record GetAllProductsQuery() implements Query<PagedResponse<ProductView>> {
    public record Result(PagedResponse<ProductView> page) implements Query.Result {}
}
