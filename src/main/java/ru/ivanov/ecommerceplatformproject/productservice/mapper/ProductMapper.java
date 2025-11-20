package ru.ivanov.ecommerceplatformproject.productservice.mapper;

import org.mapstruct.Mapper;
import ru.ivanov.ecommerceplatformproject.productservice.aggregate.Product;
import ru.ivanov.ecommerceplatformproject.productservice.dto.ProductDto;
import ru.ivanov.ecommerceplatformproject.productservice.view.ProductDetailView;
import ru.ivanov.ecommerceplatformproject.productservice.view.ProductSummaryView;
import ru.ivanov.ecommerceplatformproject.sharedlibs.dto.CartProductDto;
import ru.ivanov.ecommerceplatformproject.sharedlibs.event.ProductCreatedEvent;

@Mapper(componentModel = "spring")
public interface ProductMapper {
//    default ProductDto toDto(Product product) {
//        return new ProductDto(
//                product.getId(),
//                product.getSellerId(),
//                product.getName(),
//                product.getDescription(),
//                product.getPrimaryCategory().name(),
//                product.getPrice(),
//                product.getMainImageUrl(),
//                new ArrayList<>(product.getAdditionalImageUrls())
//        );
//    }
//
//    default FrontendProductDto toFrontedDto(Product product) {
//        return new FrontendProductDto(
//                product.getId(),
//                product.getName(),
//                product.getDescription(),
//                product.getPrimaryCategory().name(),
//                product.getPrice(),
//                product.getMainImageUrl(),
//                new ArrayList<>(product.getAdditionalImageUrls())
//        );
//    }
//
//    default SellerProductDto toSellerDto(Product product) {
//        return new SellerProductDto(
//                product.getId(),
//                product.getName(),
//                product.getDescription(),
//                product.getPrimaryCategory().name(),
//                product.getPrice(),
//                product.getMainImageUrl(),
//                new ArrayList<>(product.getAdditionalImageUrls())
//        );
//    }

    default CartProductDto toCartView(Product product) {
        return new CartProductDto(
//                product.getId(),
//                product.getName(),
//                product.getPrice(),
//                product.getMainImageUrl()
        );
    }

    default CartProductDto toCartView(ProductDto productDto) {
        return new CartProductDto(
                productDto.id(),
                productDto.name(),
                productDto.price(),
                productDto.mainImageUrl()
        );
    }


    Product toEntity(ProductCreatedEvent event);


    ProductDetailView toDetailView(Product product);

    ProductSummaryView toSummaryView(Product product);
}