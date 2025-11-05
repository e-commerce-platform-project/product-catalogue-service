package ru.ivanov.ecommerceplatformproject.productservice.model;

import jakarta.persistence.*;
import lombok.*;
import ru.ivanov.ecommerceplatformproject.productservice.model.enums.ProductCategory;
import ru.ivanov.ecommerceplatformproject.productservice.model.enums.ProductStatus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private UUID id;

    @Column(name = "seller_id", nullable = false)
    private UUID sellerId;

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
    private int availableQuantity = 0;

    @Column(name = "reserved_quantity", nullable = false)
    private int reservedQuantity;

    @Column(name = "main_image_url", nullable = false)
    private String mainImageUrl;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "product_additional_images", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "image_url")
    private List<String> additionalImageUrls = new ArrayList<>();

    public void addAdditionalImageUrl(String additionalImageUrl) {
        this.additionalImageUrls.add(additionalImageUrl);
    }

    public void updatePrice(BigDecimal newPrice) {
        if (newPrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Цена не может быть отрицательной");
        }
        this.price = newPrice;
    }

    public void updateStock(int newStockQuantity) {
        if (newStockQuantity < 0) {
            throw new IllegalArgumentException("Количество на складе не может быть отрицательным");
        }
        this.stockQuantity = newStockQuantity;
    }

    public void reserveStock(int quantity) {
        if (quantity < 0) throw new IllegalArgumentException("Количество для резервирования не может быть отрицательным");
        if (quantity > this.stockQuantity - this.reservedQuantity) {
            throw new IllegalArgumentException("Недостаточно товара для резервирования");
        }
        this.reservedQuantity += quantity;
    }

    public void unreserveStock(int quantity) {
        if (quantity < 0) throw new IllegalArgumentException("Количество для разрезервирования не может быть отрицательным");
        if (quantity > this.reservedQuantity) {
            throw new IllegalArgumentException("Нельзя разрезервировать больше, чем зарезервировано");
        }
        this.reservedQuantity -= quantity;
    }

    public void confirmReservation(int quantity) {
        this.stockQuantity -= quantity;
        this.reservedQuantity -= quantity;
    }
}