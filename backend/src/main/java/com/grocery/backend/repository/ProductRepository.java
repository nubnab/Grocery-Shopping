package com.grocery.backend.repository;

import com.grocery.backend.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsByNormalizedName(String normalizedName);

    Product findById(long id);

}
