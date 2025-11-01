package ru.ivanov.ecommerceplatformproject.productservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.ivanov.ecommerceplatformproject.common.dto.CartProductDto;
import ru.ivanov.ecommerceplatformproject.common.dto.SellerProductDto;
import ru.ivanov.ecommerceplatformproject.productservice.dto.FrontendProductDto;
import ru.ivanov.ecommerceplatformproject.productservice.dto.ProductDto;
import ru.ivanov.ecommerceplatformproject.productservice.dto.request.CreateProductRequest;
import ru.ivanov.ecommerceplatformproject.productservice.dto.request.UpdateProductRequest;
import ru.ivanov.ecommerceplatformproject.productservice.dto.response.PagedResponse;
import ru.ivanov.ecommerceplatformproject.productservice.exception.ProductNotFoundException;
import ru.ivanov.ecommerceplatformproject.productservice.mapper.ProductMapper;
import ru.ivanov.ecommerceplatformproject.productservice.model.Product;
import ru.ivanov.ecommerceplatformproject.productservice.model.enums.ProductCategory;
import ru.ivanov.ecommerceplatformproject.productservice.repository.ProductRepository;
import ru.ivanov.ecommerceplatformproject.productservice.repository.specification.ProductSpecification;
import ru.ivanov.ecommerceplatformproject.productservice.service.ImageService;
import ru.ivanov.ecommerceplatformproject.productservice.service.ProductService;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static ru.ivanov.ecommerceplatformproject.productservice.util.MessageUtils.PRODUCT_NOT_FOUND_WITH_ID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CacheManager cacheManager;
    private final ImageService imageService;

    @Override
    @Transactional
    public SellerProductDto createProduct(
            UUID sellerId, CreateProductRequest request, MultipartFile mainImage, List<MultipartFile> additionalImages) {

        String mainImageUrl = imageService.uploadImage(mainImage);

        Product product = new Product(
                sellerId,
                request.name(),
                request.description(),
                ProductCategory.valueOf(request.category()),
                request.price(),
                request.stockQuantity(),
                mainImageUrl
        );

        for (MultipartFile image : additionalImages) {
            String imageUrl = imageService.uploadImage(image);
            product.addAdditionalImageUrl(imageUrl);
        }

        Product savedProduct = productRepository.save(product);
//        cacheManager.getCache("products").put(savedProduct.getId(), productMapper.toDto(savedProduct));
        return productMapper.toSellerDto(savedProduct);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CartProductDto> getProductsForCartService(List<UUID> productsIds) {
        if (productsIds == null || productsIds.isEmpty()) {
            return Collections.emptyList();
        }

        Cache cache = cacheManager.getCache("products");

        if (cache == null) {
            return findProductsBatch(productsIds).stream()
                    .map(productMapper::toCartDto)
                    .toList();
        }

        List<CartProductDto> products = new ArrayList<>();
        List<UUID> missingIDs = new ArrayList<>();

        for (UUID id : productsIds) {
            ProductDto cachedProduct = cache.get(id, ProductDto.class);
            if (cachedProduct != null) {
                products.add(productMapper.toCartDto(cachedProduct));
            } else {
                missingIDs.add(id);
            }
        }

        if (!missingIDs.isEmpty()) {
            List<ProductDto> missingProducts = findProductsBatch(missingIDs);

            missingProducts.forEach(product -> {
                cache.put(product.id(), product);
                products.add(productMapper.toCartDto(product));
            });
        }

        return products;
    }

    @Override
    @Transactional(readOnly = true)
    public CartProductDto getProductForCartService(UUID productId) {
        Cache cache = cacheManager.getCache("product");

        if (cache == null) {
            Product product = findById(productId);
            return productMapper.toCartDto(product);
        }

        Product product = findById(productId);
        ProductDto productDto = productMapper.toDto(product);
        cache.put(productDto.id(), productDto);
        return new CartProductDto(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getStockQuantity() - product.getReservedQuantity(),
                product.getMainImageUrl()
        );
    }

    private List<ProductDto> findProductsBatch(List<UUID> ids) {
        return productRepository.findAllById(ids).stream()
                .map(productMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<FrontendProductDto> getAllProductsForFrontend(Pageable pageable, ProductCategory category, BigDecimal minPrice, BigDecimal maxPrice) {
        Specification<Product> spec = Specification.allOf(
                ProductSpecification.filterByCategory(category),
                ProductSpecification.filterByMinPrice(minPrice),
                ProductSpecification.filterByMaxPrice(maxPrice)
        );
        Page<Product> page = productRepository.findAll(spec, pageable);
        return PagedResponse.fromPage(page.map(productMapper::toFrontedDto));
    }

    @Override
    @Transactional(readOnly = true)
    public FrontendProductDto findProductById(UUID productId) {
        Product product = findById(productId);
        return productMapper.toFrontedDto(product);
    }

    @Override
    @Transactional
    public SellerProductDto updateProduct(UUID productId, UpdateProductRequest request, MultipartFile newMainImage, List<MultipartFile> newAdditionalImages, List<String> imageURLsToDelete) {
        Product product = findById(productId);

        if (request.name() != null) {
            product.setName(request.name());
        }

        if (request.description() != null) {
            product.setDescription(request.description());
        }

        if (request.category() != null) {
            product.setCategory(ProductCategory.valueOf(request.category()));
        }

        if (request.price() != null) {
            product.setPrice(request.price());
        }

        if (newMainImage != null) {
            String newMainImageUrl = imageService.uploadImage(newMainImage);
            product.setMainImageUrl(newMainImageUrl);
        }

        if (newAdditionalImages != null && !newAdditionalImages.isEmpty()) {
            newAdditionalImages.forEach(image -> {
                String imageUrl = imageService.uploadImage(image);
                product.addAdditionalImageUrl(imageUrl);
            });
        }

        if (imageURLsToDelete != null) {
            imageURLsToDelete.forEach(imageService::deleteImage);
        }

        Product savedProduct = productRepository.save(product);
        return productMapper.toSellerDto(savedProduct);
    }

    @Override
    @Transactional
    public void deleteProduct(UUID productId) {
        if (!productRepository.existsById(productId)) {
            throw new ProductNotFoundException(PRODUCT_NOT_FOUND_WITH_ID.formatted(productId));
        }
        productRepository.deleteById(productId);
    }

//    @Override
//    @Transactional(readOnly = true)
//    public boolean productExists(UUID productId) {
//        return productRepository.existsById(productId);
//    }

    private Product findById(UUID productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(PRODUCT_NOT_FOUND_WITH_ID.formatted(productId)));
    }

    @Override
    public Map<String, String> getProductsCategories() {
        return Arrays.stream(ProductCategory.values())
                .collect(Collectors.toMap(
                        ProductCategory::name,
                        category -> category.russianName
                ));
    }

    @Override
    @Transactional
    public List<SellerProductDto> getSellerProducts(UUID sellerId) {
        return productRepository.findAllBySellerId(sellerId).stream()
                .map(productMapper::toSellerDto)
                .toList();
    }
}