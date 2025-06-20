package com.grocery.backend.services;

import com.grocery.backend.dto.ProductDto;
import com.grocery.backend.dto.ProductUpdateDto;
import com.grocery.backend.entities.Product;
import com.grocery.backend.exceptions.DuplicateProductException;
import com.grocery.backend.exceptions.InvalidInputException;
import com.grocery.backend.exceptions.ResourceNotFoundException;
import com.grocery.backend.mappers.ProductMapper;
import com.grocery.backend.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Transactional
    //TODO: validation
    public Product create(ProductUpdateDto productUpdateDto){
        String normalizedName = normalizeProductName(productUpdateDto.name());

        checkDuplicateProductName(normalizedName);

        Product product = Product.builder()
                .name(productUpdateDto.name())
                .price(productUpdateDto.price())
                .quantity(productUpdateDto.quantity())
                .location(productUpdateDto.location())
                .normalizedName(normalizedName)
                .build();

        return productRepository.save(product);
    }

    public List<ProductDto> getAll(){
        List<ProductDto> productsList = new ArrayList<>();
         productRepository.findAll().forEach(product ->
                 productsList.add(productMapper.toProductDto(product)));
         return productsList;
    }


    @Transactional
    //TODO: validation
    public Product update(Long id, ProductUpdateDto productUpdateDto){
        String normalizedName = normalizeProductName(productUpdateDto.name());

        Product product = productRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Product with id " + id + " not found"));

        if(!product.getNormalizedName().equals(normalizedName)){
            checkDuplicateProductName(normalizedName);
        }

        productMapper.updateProduct(productUpdateDto, product);

        return productRepository.save(product);
    }

    @Transactional
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    private String normalizeProductName(String productName) {
        String normalizedName = productName
                                    .replaceAll("[^a-zA-Z0-9]", "")
                                    .toLowerCase()
                                    .trim();

        if(normalizedName.isEmpty()){
            throw new InvalidInputException("Product name is empty or contains only symbols");
        }
        return normalizedName;
    }

    private void checkDuplicateProductName(String normalizedName){
        if (productRepository.existsByNormalizedName(normalizedName)) {
            throw new DuplicateProductException("Product with name " + normalizedName + " already exists");
        }
    }




}
