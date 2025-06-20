package com.grocery.backend.controllers;

import com.grocery.backend.dto.ProductDto;
import com.grocery.backend.entities.Product;
import com.grocery.backend.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/products")
    public ResponseEntity<String> createProduct(@RequestBody ProductDto productDto){
        productService.create(productDto);
        //TODO: implementation
        return ResponseEntity.ok().body(productDto.toString());
    }

    @GetMapping("/products")
    public ResponseEntity<String> getProducts(){
        //TODO: implementation
        return ResponseEntity.ok().body("Product controller works");
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable Long id,
                                                @RequestBody ProductDto productDto){
        //TODO: implementation
        return ResponseEntity.ok().body(id + productDto.toString());
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id){
        //TODO: implementation
        return ResponseEntity.ok().body(id + "deleted");
    }

}
