package ru.ivanov.ecommerceplatformproject.productservice.projection;

import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.ivanov.ecommerceplatformproject.productservice.aggregate.Product;
import ru.ivanov.ecommerceplatformproject.productservice.mapper.ProductMapper;
import ru.ivanov.ecommerceplatformproject.productservice.query.GetAllCartProductsQuery;
import ru.ivanov.ecommerceplatformproject.productservice.query.GetAllProductSummaryQuery;
import ru.ivanov.ecommerceplatformproject.productservice.query.GetCartProductQuery;
import ru.ivanov.ecommerceplatformproject.productservice.query.GetProductDetailQuery;
import ru.ivanov.ecommerceplatformproject.productservice.repository.ProductRepository;
import ru.ivanov.ecommerceplatformproject.productservice.view.ProductDetailView;
import ru.ivanov.ecommerceplatformproject.productservice.view.ProductSummaryView;
import ru.ivanov.ecommerceplatformproject.sharedlibs.event.ProductCreatedEvent;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductProjection {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @EventHandler
    @Transactional
    public void handle(ProductCreatedEvent event) {
        Product product = productMapper.toEntity(event);
        productRepository.save(product);
    }


    @QueryHandler
    public ProductDetailView handle(GetProductDetailQuery query) {
        return productRepository.findById(query.id())
                .map(productMapper::toDetailView)
                .orElseThrow();//todo
    }

    @QueryHandler
    public List<ProductSummaryView> handle(GetAllProductSummaryQuery query) {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toSummaryView)
                .toList();
    };

    @QueryHandler
    public CartProductView handle(GetCartProductQuery query) {
        return productRepository.findById(query.id())
                .stream()
                .map(productMapper::toCartView)
                .orElseThrow();//todo
    }

    @QueryHandler
    public List<CartProductView> handle(GetAllCartProductsQuery query) {
        return productRepository.findByIdIn(query.ids())
                .stream()
                .map(productMapper::toCartView)
                .toList();
    }

}