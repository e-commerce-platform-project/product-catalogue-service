package ru.ivanov.ecommerceplatformproject.productservice.query.getProductByIdQuery;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.ivanov.ecommerceplatformproject.productservice.query.model.ProductView;

import java.util.UUID;

//@RestController
//@RequestMapping("/api/v1/products")
//@RequiredArgsConstructor
//public class GetProductByIdQueryController {
//
//    private final GetProductByIdQueryHandler getProductByIdQueryHandler;
//
//    @GetMapping("/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    public ProductView getProductByIdQuery(@PathVariable("id") UUID id) {
//        return getProductByIdQueryHandler.handle(id);
//    }
//}
