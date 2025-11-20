package ru.ivanov.ecommerceplatformproject.productservice.query.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.ivanov.ecommerceplatformproject.productservice.model.enums.ProductStatus;
import ru.ivanov.ecommerceplatformproject.sharedlibs.enums.ProductCategory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product_views")
public class ProductView {

    @Id
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private UUID id;

    @Column(name = "seller_name")
    private String sellerName;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "brand", nullable = false, length = 100)
    private String brand;

    @Column(name = "description", nullable = false, length = 1024)
    private String description;

    @Column(name = "category", nullable = false)
    @Enumerated(EnumType.STRING)
    private ProductCategory category;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    @Column(name = "available_quantity", nullable = false)
    private int availableQuantity;

    @Column(name = "main_image_url", nullable = false)
    private String mainImageUrl;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "product_views_additional_images", joinColumns = @JoinColumn(name = "product_view_id"))
    @Column(name = "image_url")
    private List<String> additionalImageUrls = new ArrayList<>();
}