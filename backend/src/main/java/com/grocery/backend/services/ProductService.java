package com.grocery.backend.services;

import com.grocery.backend.dto.OrderDto;
import com.grocery.backend.dto.ProductDto;
import com.grocery.backend.dto.ProductUpdateDto;
import com.grocery.backend.entities.Product;
import com.grocery.backend.exceptions.DuplicateProductException;
import com.grocery.backend.exceptions.InvalidInputException;
import com.grocery.backend.exceptions.NotEnoughStockException;
import com.grocery.backend.exceptions.ResourceNotFoundException;
import com.grocery.backend.mappers.ProductMapper;
import com.grocery.backend.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

        //TODO: validate  unique location

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
    public Product update(Long id, ProductUpdateDto productUpdateDto) {
        String normalizedName = "";

        if(productUpdateDto.name() != null) {
            normalizedName = normalizeProductName(productUpdateDto.name());
        }

        Product product = productRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Product with id " + id + " not found"));

        if(!product.getNormalizedName().equals(normalizedName)){
            checkDuplicateProductName(normalizedName);
            product.setNormalizedName(normalizedName);
        }

        //TODO: validate  unique location

        productMapper.updateProduct(productUpdateDto, product);

        if(productUpdateDto.location() != null){
            product.setLocation(productUpdateDto.location());
        }

        return productRepository.save(product);
    }

    @Transactional
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    public List<Product> fetchProducts(Map<String, Integer> preparedOrder) {
        List<Product> productList = new ArrayList<>();

        for(String key : preparedOrder.keySet()){
            Product product = productRepository.findByNormalizedName(key).orElseThrow(() ->
                    new ResourceNotFoundException("Product with name " + key + " not found"));

            validateStock(product, preparedOrder.get(key));

            productList.add(product);
        }

        return productList;
    }

    public void validateStock(Product product, int quantity) {
        if(product.getQuantity() < quantity){
            throw new NotEnoughStockException("Not enough stock to fulfill your order");
        }
    }

    public String normalizeProductName(String productName) {
        String normalizedName = productName
                .replaceAll("[^a-zA-Z0-9]", "")
                .toLowerCase()
                .trim();

        if(normalizedName.isEmpty()){
            throw new InvalidInputException("Product name is empty or contains only symbols");
        }
        return normalizedName;
    }

    @Transactional
    public void updateStock(List<Product> products, Map<String, Integer> preparedOrder) {
        for (Product product : products) {
            String normalizedName = product.getNormalizedName();

            int orderQuantity = preparedOrder.get(normalizedName);

            product.setQuantity(product.getQuantity() - orderQuantity);
        }
        productRepository.saveAll(products);
    }

    private void checkDuplicateProductName(String normalizedName){
        if (productRepository.existsByNormalizedName(normalizedName)) {
            throw new DuplicateProductException("Product with name " + normalizedName + " already exists");
        }
    }

}
