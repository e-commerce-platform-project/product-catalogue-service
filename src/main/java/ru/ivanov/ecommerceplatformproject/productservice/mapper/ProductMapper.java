package ru.ivanov.ecommerceplatformproject.productservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.ivanov.ecommerceplatformproject.productservice.dto.request.CreateProductRequest;
import ru.ivanov.ecommerceplatformproject.productservice.model.Product;
import ru.ivanov.ecommerceplatformproject.sharedlibs.event.ProductApprovedEvent;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface ProductMapper {
//    default ProductDto toDto(Product product) {
//        return new ProductDto(
//                product.getId(),
//                product.getSellerId(),
//                product.getName(),
//                product.getDescription(),
//                product.getCategory().name(),
//                product.getPrice(),
//                product.getStockQuantity(),
//                product.getReservedQuantity(),
//                product.getMainImageUrl(),
//                new ArrayList<>(product.getAdditionalImageUrls())
//        );
//    }
//
//    public FrontendProductDto toFrontedDto(Product product) {
//        return new FrontendProductDto(
//                product.getId(),
//                product.getName(),
//                product.getDescription(),
//                product.getCategory().name(),
//                product.getPrice(),
//                product.getStockQuantity() - product.getReservedQuantity(),
//                product.getMainImageUrl(),
//                new ArrayList<>(product.getAdditionalImageUrls())
//        );
//    }
//
//    public SellerProductDto toSellerDto(Product product) {
//        return new SellerProductDto(
//                product.getId(),
//                product.getName(),
//                product.getDescription(),
//                product.getCategory().name(),
//                product.getPrice(),
//                product.getStockQuantity(),
//                product.getReservedQuantity(),
//                product.getMainImageUrl(),
//                new ArrayList<>(product.getAdditionalImageUrls())
//        );
//    }
//
//    public CartProductDto toCartDto(Product product) {
//        return new CartProductDto(
//                product.getId(),
//                product.getName(),
//                product.getPrice(),
//                product.getStockQuantity() - product.getReservedQuantity(),
//                product.getAdditionalImageUrls().get(0)
//        );
//    }
//
//    public CartProductDto toCartDto(ProductDto productDto) {
//        return new CartProductDto(
//                productDto.id(),
//                productDto.name(),
//                productDto.price(),
//                productDto.stockQuantity() - productDto.reservedQuantity(),
//                productDto.mainImageUrl()
//        );
//    }


    Product toEntity(ProductApprovedEvent event);
}