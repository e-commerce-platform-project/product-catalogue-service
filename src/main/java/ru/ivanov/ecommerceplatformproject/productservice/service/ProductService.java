package ru.ivanov.ecommerceplatformproject.productservice.service;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import ru.ivanov.ecommerceplatformproject.common.dto.CartProductDto;
import ru.ivanov.ecommerceplatformproject.common.dto.SellerProductDto;
import ru.ivanov.ecommerceplatformproject.productservice.dto.FrontendProductDto;
import ru.ivanov.ecommerceplatformproject.productservice.dto.request.CreateProductRequest;
import ru.ivanov.ecommerceplatformproject.productservice.dto.request.UpdateProductRequest;
import ru.ivanov.ecommerceplatformproject.productservice.dto.response.PagedResponse;
import ru.ivanov.ecommerceplatformproject.productservice.model.enums.ProductCategory;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface ProductService {

    SellerProductDto createProduct(UUID sellerId, CreateProductRequest request, MultipartFile mainImage, List<MultipartFile> additionalImages);

    PagedResponse<FrontendProductDto> getAllProductsForFrontend(Pageable pageable, ProductCategory category, BigDecimal minPrice, BigDecimal maxPrice);

    List<SellerProductDto> getSellerProducts(UUID sellerId);

    List<CartProductDto> getProductsForCartService(List<UUID> productsIDs);

    CartProductDto getProductForCartService(UUID productId);

    FrontendProductDto findProductById(UUID productId);

    SellerProductDto updateProduct(UUID productId, UpdateProductRequest request, MultipartFile newMainImage, List<MultipartFile> newAdditionalImages, List<String> deletedImagesURLs);

    void deleteProduct(UUID productId);

//    boolean productExists(UUID productId);

    Map<String, String> getProductsCategories();
}