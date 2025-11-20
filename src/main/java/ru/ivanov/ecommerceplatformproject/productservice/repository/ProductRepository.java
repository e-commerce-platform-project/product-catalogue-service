package ru.ivanov.ecommerceplatformproject.productservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.ivanov.ecommerceplatformproject.productservice.aggregate.Product;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID>, JpaSpecificationExecutor<Product> {
    List<Product> findAllBySellerId(UUID sellerId);

    List<CartProductView> findByIdIn(Collection<UUID> ids);
}