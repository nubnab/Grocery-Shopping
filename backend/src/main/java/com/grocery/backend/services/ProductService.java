package com.grocery.backend.services;

import com.grocery.backend.dto.ProductDto;
import com.grocery.backend.entities.Product;
import com.grocery.backend.exceptions.DuplicateProductException;
import com.grocery.backend.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public void create(ProductDto productDto){

        String normalizedName = normalizeProductName(productDto.name());

        if (productRepository.existsByNormalizedName(normalizedName)) {
            throw new DuplicateProductException(
                    "Product with name " + normalizedName + " already exists"
            );
        }

        Product product = Product.builder()
                .name(productDto.name())
                .price(productDto.price())
                .quantity(productDto.quantity())
                .location(productDto.location())
                .normalizedName(normalizedName)
                .build();

        productRepository.save(product);
    }
    
    private String normalizeProductName(String productName){
        return productName
                .replaceAll("[^a-zA-Z0-9]", "")
                .toLowerCase()
                .trim();
    }


}
