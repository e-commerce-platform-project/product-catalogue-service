package ru.ivanov.ecommerceplatformproject.productservice.controller;

import lombok.RequiredArgsConstructor;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.*;
import ru.ivanov.ecommerceplatformproject.productservice.query.GetAllCartProductsQuery;
import ru.ivanov.ecommerceplatformproject.productservice.query.GetCartProductQuery;
import ru.ivanov.ecommerceplatformproject.sharedlibs.dto.CartProductDto;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/internal/api/v1/products")
@RequiredArgsConstructor
public class InternalRestProductControllerV1 {

    private final QueryGateway queryGateway;

    @GetMapping("/cart")
    public CompletableFuture<List<CartProductDto>> getProductsForCartService(
            @RequestParam(name = "ids") List<UUID> productsIDs
    ) {
        return queryGateway.query(new GetAllCartProductsQuery(productsIDs), ResponseTypes.multipleInstancesOf(CartProductDto.class));
    }

    @GetMapping("/cart/{productId}")
    public CompletableFuture<CartProductDto> getProductForCartService(@PathVariable("productId") UUID productId) {
        return queryGateway.query(new GetCartProductQuery(productId), ResponseTypes.instanceOf(CartProductDto.class));
    }

//    @GetMapping("/seller")
//    public ResponseEntity<List<SellerProductDto>> getProductsForSellerService() {
//        UUID sellerId = null;
//        List<SellerProductDto> products = productService.getSellerProducts(sellerId);
//        return ResponseEntity.ok()
//                .contentType(APPLICATION_JSON)
//                .body(products);
//    }
}
