package ru.ivanov.ecommerceplatformproject.productservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.ivanov.ecommerceplatformproject.common.dto.CartProductDto;
import ru.ivanov.ecommerceplatformproject.common.dto.SellerProductDto;
import ru.ivanov.ecommerceplatformproject.productservice.dto.FrontendProductDto;
import ru.ivanov.ecommerceplatformproject.productservice.dto.request.CreateProductRequest;
import ru.ivanov.ecommerceplatformproject.productservice.dto.request.UpdateProductRequest;
import ru.ivanov.ecommerceplatformproject.productservice.dto.response.PagedResponse;
import ru.ivanov.ecommerceplatformproject.productservice.model.enums.ProductCategory;
import ru.ivanov.ecommerceplatformproject.productservice.security.JwtPrincipal;
import ru.ivanov.ecommerceplatformproject.productservice.service.ProductService;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequestMapping("api/v1/products")
@RequiredArgsConstructor
public class ProductRestController {

    private final ProductService productService;

    @PostMapping(consumes = MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<SellerProductDto> createProduct(
            @RequestPart("product") @Valid CreateProductRequest request,
            @RequestPart("mainImage") MultipartFile mainImage,
            @RequestPart(value = "additionalImage", required = false) List<MultipartFile> additionalImages,
            @AuthenticationPrincipal JwtPrincipal principal
    ) {
        UUID sellerId = principal.getId();
        SellerProductDto product = productService.createProduct(sellerId, request, mainImage, additionalImages);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{productId}")
                .buildAndExpand(product.id())
                .toUri();
        return ResponseEntity.created(location)
                .contentType(APPLICATION_JSON)
                .body(product);
    }

    @GetMapping
    public ResponseEntity<PagedResponse<FrontendProductDto>> getAllProductsForFrontend(
            @RequestParam(name = "page", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(name = "size", required = false, defaultValue = "10")  int pageSize,
            @RequestParam(name = "category", required = false) ProductCategory category,
            @RequestParam(name = "minPrice", required = false) BigDecimal minPrice,
            @RequestParam(name = "maxPrice", required = false) BigDecimal maxPrice
    ) {
        PagedResponse<FrontendProductDto> page = productService.getAllProductsForFrontend(
                PageRequest.of(pageNumber, pageSize),
                category,
                minPrice,
                maxPrice
        );
        return ResponseEntity.ok()
                .contentType(APPLICATION_JSON)
                .body(page);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<FrontendProductDto> getProductForFrontend(@PathVariable("productId") UUID productId) {
        FrontendProductDto product = productService.findProductById(productId);
        return ResponseEntity.ok()
                .contentType(APPLICATION_JSON)
                .body(product);
    }

    @GetMapping("/cart")
//    @PreAuthorize("hasRole('CART-SERVICE')")
    public ResponseEntity<List<CartProductDto>> getProductsForCartService(
            @RequestParam(name = "ids") List<UUID> productsIDs
    ) {
        List<CartProductDto> products = productService.getProductsForCartService(productsIDs);
        return ResponseEntity.ok()
                .contentType(APPLICATION_JSON)
                .body(products);
    }

    @GetMapping("/cart/{productId}")
//    @PreAuthorize("hasRole('CART-SERVICE')")
    public ResponseEntity<CartProductDto> getProductForCartService(@PathVariable("productId") UUID productId) {
        CartProductDto product = productService.getProductForCartService(productId);
        return ResponseEntity.ok()
                .contentType(APPLICATION_JSON)
                .body(product);
    }

    @GetMapping("/seller")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<List<SellerProductDto>> getProductsForSellerService(
            @AuthenticationPrincipal JwtPrincipal principalDetails
    ) {
        UUID sellerId = principalDetails.getId();
        List<SellerProductDto> products = productService.getSellerProducts(sellerId);
        return ResponseEntity.ok()
                .contentType(APPLICATION_JSON)
                .body(products);
    }

    @GetMapping("/categories")
    public ResponseEntity<Map<String, String>> getProductCategories() {
        Map<String, String> categories = productService.getProductsCategories();
        return ResponseEntity.ok()
                .contentType(APPLICATION_JSON)
                .body(categories);
    }

    @PatchMapping(value = "/{productId}", consumes = MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<SellerProductDto> updateProduct(
            @PathVariable("productId") UUID productId,
            @RequestPart("product") @Valid UpdateProductRequest request,
            @RequestPart(value = "newMainImage", required = false) MultipartFile newMainImage,
            @RequestPart(value = "newAdditionalImages", required = false) List<MultipartFile> newAdditionalImages,
            @RequestPart(value = "deletedImages", required = false) List<String> imageURLsToDelete
    ) {
        SellerProductDto updatedProduct = productService.updateProduct(productId, request, newMainImage, newAdditionalImages, imageURLsToDelete);
        return ResponseEntity.ok()
                .contentType(APPLICATION_JSON)
                .body(updatedProduct);
    }

    @DeleteMapping("/{productId}")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<Void> deleteProduct(@PathVariable("productId") UUID productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }
}