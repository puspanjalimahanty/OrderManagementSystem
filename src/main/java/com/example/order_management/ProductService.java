package com.example.order_management;

import java.util.List;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }
    private static final Logger logger =
            LoggerFactory.getLogger(ProductService.class);

    public ProductResponseDTO createProduct(ProductRequestDTO dto) {

        logger.info("Creating product: {}", dto.getName());

        Product product = new Product();

        product.setName(dto.getName());
        product.setPrice(dto.getPrice());

        Product savedProduct = repository.save(product);

        logger.info("Product created successfully with id: {}",
                savedProduct.getId());

        return convertToResponse(savedProduct);
    }

    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    private ProductResponseDTO convertToResponse(Product product) {

        ProductResponseDTO response = new ProductResponseDTO();

        response.setId(product.getId());
        response.setName(product.getName());
        response.setPrice(product.getPrice());

        return response;
    }
}