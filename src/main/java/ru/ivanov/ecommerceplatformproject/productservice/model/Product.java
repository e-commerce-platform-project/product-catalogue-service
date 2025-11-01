package ru.ivanov.ecommerceplatformproject.productservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.ivanov.ecommerceplatformproject.productservice.model.enums.ProductCategory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "seller_id")
    private UUID sellerId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private ProductCategory category;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "stock_quantity")
    private int stockQuantity;

    @Column(name = "reserved_quantity")
    private int reservedQuantity;

    @Column(name = "main_image_url")
    private String mainImageUrl;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "product_additional_images", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "image_url")
    private List<String> additionalImageUrls = new ArrayList<>();

    public Product(
            UUID sellerId,
            String name,
            String description,
            ProductCategory category,
            BigDecimal price,
            int stockQuantity,
            String mainImageUrl
    ) {
        this.sellerId = sellerId;
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.reservedQuantity = 0;
        this.mainImageUrl = mainImageUrl;
    }

    public void addAdditionalImageUrl(String additionalImageUrl) {
        this.additionalImageUrls.add(additionalImageUrl);
    }
}