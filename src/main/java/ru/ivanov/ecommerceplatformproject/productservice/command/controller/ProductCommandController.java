package ru.ivanov.ecommerceplatformproject.productservice.command.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ivanov.ecommerceplatformproject.productservice.command.service.ProductCommandService;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductCommandController {

    private final ProductCommandService productCommandServiceImpl;


}