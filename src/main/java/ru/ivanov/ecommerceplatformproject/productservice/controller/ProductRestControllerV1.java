package ru.ivanov.ecommerceplatformproject.productservice.controller;

import lombok.RequiredArgsConstructor;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import ru.ivanov.ecommerceplatformproject.productservice.dto.response.PagedResponse;
import ru.ivanov.ecommerceplatformproject.productservice.query.GetAllProductsQuery;
import ru.ivanov.ecommerceplatformproject.productservice.query.GetProductDetailQuery;
import ru.ivanov.ecommerceplatformproject.productservice.query.handler.GetAllProductsQueryHandler;
import ru.ivanov.ecommerceplatformproject.productservice.query.handler.GetProductDetailQueryHandler;
import ru.ivanov.ecommerceplatformproject.productservice.service.ProductService;
import ru.ivanov.ecommerceplatformproject.productservice.view.ProductDetailView;
import ru.ivanov.ecommerceplatformproject.productservice.view.ProductSummaryView;
import ru.ivanov.ecommerceplatformproject.sharedlibs.enums.ProductCategory;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("api/v1/products")
@RequiredArgsConstructor
public class ProductRestControllerV1 {

    private final ProductService productService;
    private final GetAllProductsQueryHandler getAllProductsQueryHandler;
    private final GetProductDetailQueryHandler getProductDetailQueryHandler;

    @GetMapping
    public CompletableFuture<PagedResponse<ProductSummaryView>> getAllProducts(
            @PageableDefault Pageable pageable,
            @RequestParam(name = "category", required = false) ProductCategory category,
            @RequestParam(name = "minPrice", required = false) BigDecimal minPrice,
            @RequestParam(name = "maxPrice", required = false) BigDecimal maxPrice
    ) {
        return CompletableFuture.supplyAsync(
                () -> getAllProductsQueryHandler.handle(
                        new GetAllProductsQuery(pageable, category, minPrice, maxPrice))
        );
    }

    @GetMapping("/{productId}")
    public CompletableFuture<ProductDetailView> getProductById(@PathVariable UUID productId) {
        return CompletableFuture.supplyAsync(
                () -> )
        return queryGateway.query(new GetProductDetailQuery(productId), ResponseTypes.instanceOf(ProductDetailView.class));
    }


    @GetMapping("/categories")
    public Map<String, String> getProductCategories() {
        return productService.getProductsCategories();
    }
}