package org.shivanishukla.restaurant.repo;

import org.shivanishukla.restaurant.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ProductRepo extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE p.productPrice BETWEEN :minPrice AND :maxPrice ORDER BY p.productPrice desc limit 2")
    List<Product> findTop2ProductsByPriceRange(Double minPrice, Double maxPrice);

}

