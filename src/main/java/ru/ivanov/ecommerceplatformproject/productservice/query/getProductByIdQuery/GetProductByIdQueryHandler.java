package ru.ivanov.ecommerceplatformproject.productservice.query.getProductByIdQuery;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ivanov.ecommerceplatformproject.productservice.query.model.ProductView;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetProductByIdQueryHandler {

    public ProductView handle(UUID productId) {
        return null;
    }
}
