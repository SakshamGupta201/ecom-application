
package com.app.ecom_application.service;

import java.util.List;
import java.util.Optional;

import com.app.ecom_application.dto.ProductRequestDTO;
import com.app.ecom_application.dto.ProductResponseDTO;

public interface ProductService {
    /**
     * Creates a new product based on the provided product request DTO.
     *
     * @param productRequest the product request DTO containing product details
     * @return the created product response DTO
     */
    public Optional<ProductResponseDTO> createProduct(ProductRequestDTO productRequest);

    /**
     * Retrieves all products available in the system.
     *
     * @return a list of product response DTOs representing all products
     */
    public List<ProductResponseDTO> getAllProducts();

    /**
     * Retrieves a specific product by its ID.
     *
     * @param id the unique identifier of the product
     * @return the product response DTO for the requested product
     */
    public Optional<ProductResponseDTO> getProductById(Integer id);

    /**
     * Updates an existing product with the provided information.
     *
     * @param id the unique identifier of the product to update
     * @param productRequest the product request DTO containing updated product details
     * @return the updated product response DTO
     */
    public Optional<ProductResponseDTO> updateProduct(Integer id, ProductRequestDTO productRequest);

    /**
     * Deletes a product from the system by its ID.
     *
     * @param id the unique identifier of the product to delete
     */
    public boolean deleteProduct(Integer id);
}
