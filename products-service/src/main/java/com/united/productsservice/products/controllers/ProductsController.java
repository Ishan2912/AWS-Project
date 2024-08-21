package com.united.productsservice.products.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductsController {
    private static final Logger LOG = LoggerFactory.getLogger(ProductsController.class);

    @GetMapping
    public String getAllProducts() {
        LOG.info("Get all products");
        return "All products";
    }
}
