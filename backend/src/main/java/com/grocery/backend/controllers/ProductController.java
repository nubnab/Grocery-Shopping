package com.grocery.backend.controllers;

import com.grocery.backend.dto.ProductDto;
import com.grocery.backend.entities.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController {

    @GetMapping("/products")
    public ResponseEntity<String> getProducts(){
        //TODO: implementation
        return ResponseEntity.ok().body("Product controller works");
    }

    @PostMapping("/products")
    public ResponseEntity<String> createProduct(@RequestBody ProductDto productDto){
        //TODO: implementation
        return ResponseEntity.ok().body(productDto.toString());
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
