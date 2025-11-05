package ru.ivanov.ecommerceplatformproject.productservice.command.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.ivanov.ecommerceplatformproject.productservice.command.service.ProductCommandService;
import ru.ivanov.ecommerceplatformproject.productservice.command.service.S3Service;
import ru.ivanov.ecommerceplatformproject.productservice.dto.request.CreateProductRequest;
import ru.ivanov.ecommerceplatformproject.productservice.mapper.ProductMapper;
import ru.ivanov.ecommerceplatformproject.productservice.model.Product;
import ru.ivanov.ecommerceplatformproject.productservice.repository.ProductRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductCommandServiceImpl implements ProductCommandService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final S3Service s3Service;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    private static final String TOPIC = "product.events";//todo

    public UUID createProduct(UUID sellerId, CreateProductRequest request, MultipartFile mainImage, List<MultipartFile> additionalImages) {
        String mainImageUrl = s3Service.uploadFile(mainImage);

        List<String> additionalImageUrls = additionalImages.stream()
                .map(s3Service::uploadFile)
                .toList();

        Product product = productMapper.toEntity(
                sellerId,
                request,
                mainImageUrl,
                additionalImageUrls
        );

        Product savedProduct = productRepository.save(product);

        ProductCreatedEvent event = ProductCreatedEvent.builder()
                .productId(savedProduct.getId())
                .sellerId(savedProduct.getSellerId())
                .name()
                .brand()
                .description()
                .category()
                .price()
                .stockQuantity()
                .mainImageUrl()
                .additionalImageUrls()
                .build();

        kafkaTemplate.send(TOPIC, event.productId().toString(), event);

        return savedProduct.getId();
    }
}