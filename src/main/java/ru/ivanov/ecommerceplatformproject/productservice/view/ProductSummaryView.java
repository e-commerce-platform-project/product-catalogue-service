package ru.ivanov.ecommerceplatformproject.productservice.view;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity(name = "ProductSummary")
public class ProductSummaryView {

    @Id
    private UUID id;





}