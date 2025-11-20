package ru.ivanov.ecommerceplatformproject.productservice.query.getAllProductsQuery;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.ivanov.ecommerceplatformproject.productservice.dto.response.PagedResponse;
import ru.ivanov.ecommerceplatformproject.productservice.query.model.ProductView;
import ru.ivanov.ecommerceplatformproject.sharedlibs.enums.ProductCategory;

import java.math.BigDecimal;

//@RestController
//@RequestMapping("/api/v1/products")
//@RequiredArgsConstructor
//public class GetAllProductsQueryController {
//
//    private final GetAllProductsQueryHandler getAllProductsQueryHandler;
//
//    @GetMapping
//    @ResponseStatus(HttpStatus.OK)
//    public PagedResponse<ProductView> getAllProductsQuery(
//            @PageableDefault Pageable pageable,
//            @RequestParam(name = "category", required = false) ProductCategory category,
//            @RequestParam(name = "minPrice", required = false) BigDecimal minPrice,
//            @RequestParam(name = "maxPrice", required = false) BigDecimal maxPrice
//    ) {
////        return getAllProductsQueryHandler.handle(pageable, category, minPrice, maxPrice);
//    }
//}
