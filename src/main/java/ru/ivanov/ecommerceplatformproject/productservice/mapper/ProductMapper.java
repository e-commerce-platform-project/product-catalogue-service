package ru.ivanov.ecommerceplatformproject.productservice.mapper;

import org.springframework.stereotype.Component;
import ru.ivanov.ecommerceplatformproject.common.dto.CartProductDto;
import ru.ivanov.ecommerceplatformproject.common.dto.SellerProductDto;
import ru.ivanov.ecommerceplatformproject.productservice.dto.FrontendProductDto;
import ru.ivanov.ecommerceplatformproject.productservice.dto.ProductDto;
import ru.ivanov.ecommerceplatformproject.productservice.model.Product;

import java.util.ArrayList;

@Component
public class ProductMapper {
    public ProductDto toDto(Product product) {
        return new ProductDto(
                product.getId(),
                product.getSellerId(),
                product.getName(),
                product.getDescription(),
                product.getCategory().name(),
                product.getPrice(),
                product.getStockQuantity(),
                product.getReservedQuantity(),
                product.getMainImageUrl(),
                new ArrayList<>(product.getAdditionalImageUrls())
        );
    }

    public FrontendProductDto toFrontedDto(Product product) {
        return new FrontendProductDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getCategory().name(),
                product.getPrice(),
                product.getStockQuantity() - product.getReservedQuantity(),
                product.getMainImageUrl(),
                new ArrayList<>(product.getAdditionalImageUrls())
        );
    }

    public SellerProductDto toSellerDto(Product product) {
        return new SellerProductDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getCategory().name(),
                product.getPrice(),
                product.getStockQuantity(),
                product.getReservedQuantity(),
                product.getMainImageUrl(),
                new ArrayList<>(product.getAdditionalImageUrls())
        );
    }

    public CartProductDto toCartDto(Product product) {
        return new CartProductDto(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getStockQuantity() - product.getReservedQuantity(),
                product.getAdditionalImageUrls().get(0)
        );
    }

    public CartProductDto toCartDto(ProductDto productDto) {
        return new CartProductDto(
                productDto.id(),
                productDto.name(),
                productDto.price(),
                productDto.stockQuantity() - productDto.reservedQuantity(),
                productDto.mainImageUrl()
        );
    }
}