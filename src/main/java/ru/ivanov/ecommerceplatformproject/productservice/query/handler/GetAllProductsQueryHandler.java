package ru.ivanov.ecommerceplatformproject.productservice.query.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.ivanov.ecommerceplatformproject.productservice.dto.response.PagedResponse;
import ru.ivanov.ecommerceplatformproject.productservice.query.GetAllProductsQuery;
import ru.ivanov.ecommerceplatformproject.productservice.query.Query;
import ru.ivanov.ecommerceplatformproject.productservice.repository.ProductRepository;
import ru.ivanov.ecommerceplatformproject.productservice.view.ProductSummaryView;

@Component
@RequiredArgsConstructor
public class GetAllProductsQueryHandler implements Query.Handler<GetAllProductsQuery, PagedResponse<ProductSummaryView>> {

    private final ProductRepository productRepository;


    @Override
    public PagedResponse<ProductSummaryView> handle(GetAllProductsQuery query) {
        return null;
    }
}