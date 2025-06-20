package com.grocery.backend.controllers;

import com.grocery.backend.dto.ProductResponseDto;
import com.grocery.backend.dto.ProductDto;
import com.grocery.backend.dto.ProductUpdateDto;
import com.grocery.backend.entities.Product;
import com.grocery.backend.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/products")
    public ResponseEntity<ProductResponseDto> createProduct(@RequestBody ProductUpdateDto productUpdateDto){
        //TODO: shift URI creation to service?
        Product product = productService.create(productUpdateDto);
        return ResponseEntity.created(URI.create("/products/" + product.getId())).body(
                new ProductResponseDto(product.getId(), product.getName())
        );
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> getProducts(){
        return ResponseEntity.ok(productService.getAll());
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable Long id,
                                                            @RequestBody ProductUpdateDto ProductUpdateDto){
        //TODO: shift URI creation to service?
        Product product = productService.update(id, ProductUpdateDto);
        return ResponseEntity.created(URI.create("/products/" + product.getId())).body(
                new ProductResponseDto(product.getId(), product.getName())
        );
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id){
        //TODO: revisit
        productService.delete(id);
        return ResponseEntity.ok().body(id + "deleted");
    }

}
