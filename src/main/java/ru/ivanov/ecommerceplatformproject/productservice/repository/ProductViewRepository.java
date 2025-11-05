package ru.ivanov.ecommerceplatformproject.productservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ivanov.ecommerceplatformproject.productservice.query.model.ProductView;

import java.util.UUID;

@Repository
public interface ProductViewRepository extends JpaRepository<ProductView, UUID> {
}
