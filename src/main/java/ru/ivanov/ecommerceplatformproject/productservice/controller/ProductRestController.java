package ru.ivanov.ecommerceplatformproject.productservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ivanov.ecommerceplatformproject.productservice.dto.FrontendProductDto;
import ru.ivanov.ecommerceplatformproject.productservice.dto.response.PagedResponse;
import ru.ivanov.ecommerceplatformproject.productservice.service.ProductService;
import ru.ivanov.ecommerceplatformproject.sharedlibs.dto.CartProductDto;
import ru.ivanov.ecommerceplatformproject.sharedlibs.enums.ProductCategory;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/products")
@RequiredArgsConstructor
public class ProductRestController {

    private final ProductService productService;

    @GetMapping
    public PagedResponse<FrontendProductDto> getAllProductsForFrontend(
            @PageableDefault Pageable pageable,
            @RequestParam(name = "category", required = false) ProductCategory category,
            @RequestParam(name = "minPrice", required = false) BigDecimal minPrice,
            @RequestParam(name = "maxPrice", required = false) BigDecimal maxPrice
    ) {
        return  productService.getAllProductsForFrontend(
                pageable,
                category,
                minPrice,
                maxPrice
        );
    }

    @GetMapping("/{productId}")
    public FrontendProductDto getProductForFrontend(@PathVariable UUID productId) {
        return productService.findProductById(productId);
    }

//    @GetMapping("/cart")
//    public ResponseEntity<List<CartProductDto>> getProductsForCartService(
//            @RequestParam(name = "ids") List<UUID> productsIDs
//    ) {
//        List<CartProductDto> products = productService.getProductsForCartService(productsIDs);
//        return ResponseEntity.ok()
//                .contentType(APPLICATION_JSON)
//                .body(products);
//    }

    @GetMapping("/cart/{productId}")
    public CartProductDto getProductForCartService(@PathVariable("productId") UUID productId) {
        return productService.getProductForCartService(productId);
    }

//    @GetMapping("/seller")
//    public ResponseEntity<List<SellerProductDto>> getProductsForSellerService() {
//        UUID sellerId = null;
//        List<SellerProductDto> products = productService.getSellerProducts(sellerId);
//        return ResponseEntity.ok()
//                .contentType(APPLICATION_JSON)
//                .body(products);
//    }

    @GetMapping("/categories")
    public Map<String, String> getProductCategories() {
        return productService.getProductsCategories();
    }
}