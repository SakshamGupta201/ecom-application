package com.app.ecom_application.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.app.ecom_application.dto.ProductRequestDTO;
import com.app.ecom_application.dto.ProductResponseDTO;
import com.app.ecom_application.models.Product;
import com.app.ecom_application.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public Optional<ProductResponseDTO> createProduct(ProductRequestDTO productRequest) {
        Product product = mapToProduct(productRequest, null);
        Product savedProduct = productRepository.save(product);
        return Optional.of(mapToResponse(savedProduct));
    }

    @Override
    public Optional<ProductResponseDTO> getProductById(Integer id) {
        return productRepository.findById(id)
                .map(this::mapToResponse);
    }

    @Override
    public List<ProductResponseDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::mapToResponse)
                .filter(product -> product.getIsActive())
                .toList();
    }

    @Override
    public Optional<ProductResponseDTO> updateProduct(Integer id, ProductRequestDTO productRequest) {
        return productRepository.findById(id)
                .map(existingProduct -> {
                    Product updatedProductEntity = mapToProduct(productRequest, existingProduct);
                    Product updatedProduct = productRepository.save(updatedProductEntity);
                    return mapToResponse(updatedProduct);
                });
    }

    @Override
    public boolean deleteProduct(Integer id) {
        return productRepository.findById(id)
                .map(product -> {
                    productRepository.delete(product);
                    return true;
                })
                .orElse(false);
    }

    private ProductResponseDTO mapToResponse(Product product) {
        ProductResponseDTO response = new ProductResponseDTO();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setPrice(product.getPrice());
        response.setStockQuantity(product.getStockQuantity());
        response.setIsActive(product.getIsActive());
        response.setImageUrl(product.getImageUrl());
        response.setCreatedAt(product.getCreatedAt().toString());
        response.setUpdatedAt(product.getUpdatedAt().toString());
        return response;
    }

    /**
     * Maps ProductRequestDTO to Product entity. If existingProduct is null, creates new Product, else updates the existing one.
     */
    private Product mapToProduct(ProductRequestDTO productRequest, Product existingProduct) {
        Product product = (existingProduct == null) ? new Product() : existingProduct;
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setStockQuantity(productRequest.getStockQuantity());
        product.setIsActive(productRequest.getIsActive());
        product.setImageUrl(productRequest.getImageUrl());
        return product;
    }
}
