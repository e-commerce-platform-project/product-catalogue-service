package ru.ivanov.ecommerceplatformproject.productservice.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.modelling.command.AggregateMember;
import org.axonframework.spring.stereotype.Aggregate;
import ru.ivanov.ecommerceplatformproject.productservice.command.CreateProductCommand;
import ru.ivanov.ecommerceplatformproject.productservice.model.enums.ProductStatus;
import ru.ivanov.ecommerceplatformproject.productservice.valueObject.*;
import ru.ivanov.ecommerceplatformproject.sharedlibs.enums.ProductCategory;
import ru.ivanov.ecommerceplatformproject.sharedlibs.event.ProductCreatedEvent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Aggregate
public class Product {

    @AggregateIdentifier
    private ProductId id;

    private SellerId sellerId;

    private ProductName name;

    private Brand brand;

    private ProductDescription description;

    private ProductCategory primaryCategory;

    private Set<ProductCategory> additionalCategories = new HashSet<>();

    private Money price;

    private ProductStatus status;

    private ImageUrl mainImageUrl;

    @AggregateMember
    private List<ImageUrl> additionalImageUrls = new ArrayList<>();

    @CommandHandler
    public void handle(CreateProductCommand command) {
        AggregateLifecycle.apply(new ProductCreatedEvent(
                command.id(),
                command.sellerId(),
                command.name(),
                command.description(),
                command.category(),
                command.brand(),
                command.price(),
                command.mainImageUrl(),
                command.additionalImageUrls()
        ));
    }

    @EventSourcingHandler
    public void on(ProductCreatedEvent event) {
        this.id = new ProductId(event.id());
        this.sellerId = new SellerId(event.sellerId());
        this.name = new ProductName(event.name());
        this.brand = new Brand(event.brand());
        this.description = new ProductDescription(event.description());
        this.primaryCategory = event.category();
        this.price = new Money(event.price());
        this.status = ProductStatus.NEW;
        this.mainImageUrl = new ImageUrl(event.mainImageUrl());
        this.additionalImageUrls = event.additionalImageUrls().stream()
                .map(ImageUrl::new)
                .toList();
    }

    @CommandHandler
    public void handle()

}