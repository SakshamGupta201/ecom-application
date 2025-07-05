package com.app.ecom_application.dto;

import lombok.Data;

@Data
public class ProductResponseDTO {
    private Integer id;
    private String name;
    private String description;
    private Double price;
    private Integer stockQuantity; 
}
