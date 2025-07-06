package com.app.ecom_application.controllers;

import java.util.List;

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

import com.app.ecom_application.dto.ProductRequestDTO;
import com.app.ecom_application.dto.ProductResponseDTO;
import com.app.ecom_application.service.ProductService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody ProductRequestDTO productRequest) {
        return productService.createProduct(productRequest)
                .map(createdProduct -> new ResponseEntity<>(createdProduct, HttpStatus.CREATED))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
        List<ProductResponseDTO> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Integer id) {
        return productService.getProductById(id)
                .map(product -> new ResponseEntity<>(product, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable Integer id,
            @RequestBody ProductRequestDTO productRequest) {
        return productService.updateProduct(id, productRequest)
                .map(updatedProduct -> new ResponseEntity<>(updatedProduct, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Integer id) {
        return productService.getProductById(id)
                .map(_ -> {
                    productService.deleteProduct(id);
                    return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
                })
                .orElseGet(() -> new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND));
    }

}
