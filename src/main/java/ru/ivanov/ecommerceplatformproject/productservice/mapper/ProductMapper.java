package ru.ivanov.ecommerceplatformproject.productservice.mapper;

import org.mapstruct.Mapper;
import ru.ivanov.ecommerceplatformproject.productservice.dto.FrontendProductDto;
import ru.ivanov.ecommerceplatformproject.productservice.dto.ProductDto;
import ru.ivanov.ecommerceplatformproject.productservice.model.Product;
import ru.ivanov.ecommerceplatformproject.sharedlibs.dto.CartProductDto;
import ru.ivanov.ecommerceplatformproject.sharedlibs.dto.SellerProductDto;
import ru.ivanov.ecommerceplatformproject.sharedlibs.event.ProductApprovedEvent;

import java.util.ArrayList;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    default ProductDto toDto(Product product) {
        return new ProductDto(
                product.getId(),
                product.getSellerId(),
                product.getName(),
                product.getDescription(),
                product.getPrimaryCategory().name(),
                product.getPrice(),
                product.getMainImageUrl(),
                new ArrayList<>(product.getAdditionalImageUrls())
        );
    }

    default FrontendProductDto toFrontedDto(Product product) {
        return new FrontendProductDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrimaryCategory().name(),
                product.getPrice(),
                product.getMainImageUrl(),
                new ArrayList<>(product.getAdditionalImageUrls())
        );
    }

    default SellerProductDto toSellerDto(Product product) {
        return new SellerProductDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrimaryCategory().name(),
                product.getPrice(),
                product.getMainImageUrl(),
                new ArrayList<>(product.getAdditionalImageUrls())
        );
    }

    default CartProductDto toCartDto(Product product) {
        return new CartProductDto(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getMainImageUrl()
        );
    }

    default CartProductDto toCartDto(ProductDto productDto) {
        return new CartProductDto(
                productDto.id(),
                productDto.name(),
                productDto.price(),
                productDto.mainImageUrl()
        );
    }


    Product toEntity(ProductApprovedEvent event);
}