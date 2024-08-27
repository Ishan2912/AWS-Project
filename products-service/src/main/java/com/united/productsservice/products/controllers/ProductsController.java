package com.united.productsservice.products.controllers;

import com.united.productsservice.products.dto.ProductDto;
import com.united.productsservice.products.model.Product;
import com.united.productsservice.products.repository.ProductsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletionException;

@RestController
@RequestMapping("/api/products")
public class ProductsController {
    private static final Logger LOG = LoggerFactory.getLogger(ProductsController.class);
    private final ProductsRepository productsRepository;

    @Autowired
    public ProductsController(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        LOG.info("Get all products");
        List<ProductDto> lstProductDto = new ArrayList<>();
        productsRepository.getAll().items().subscribe(
                product -> lstProductDto.add(new ProductDto(product))).join();

        return new ResponseEntity<>(lstProductDto, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable("id") String id) {
        LOG.info("Get product by id: {}", id);
        Product product = productsRepository.getById(id).join();
        if (product == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new ProductDto(product), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        LOG.info("Create product: {}", productDto);
        Product product = ProductDto.toProduct(productDto);
        product.setId(UUID.randomUUID().toString());
        productsRepository.createProduct(product).join();
        return new ResponseEntity<>(new ProductDto(product), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") String id) {
        LOG.info("Delete product by id: {}", id);
        Product deletedProduct = productsRepository.deleteById(id).join();
        if(deletedProduct != null){
            return new ResponseEntity<>(new ProductDto(deletedProduct), HttpStatus.OK);
        }
        return new ResponseEntity<>("Product not found!", HttpStatus.NOT_FOUND);
    }

    @PutMapping("id")
    public ResponseEntity<?> updateProduct(@RequestBody ProductDto productDto,
                                           @PathVariable("id") String id) {
        LOG.info("Update product by id: {}", id);
        try {
            Product updatedProduct = productsRepository.update(ProductDto.toProduct(productDto), id).join();
            return new ResponseEntity<>(new ProductDto(updatedProduct), HttpStatus.OK);
        } catch (CompletionException ex){
            return new ResponseEntity<>("Product not found!", HttpStatus.NOT_FOUND);
        }
    }
}
