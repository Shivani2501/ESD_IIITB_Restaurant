package org.shivanishukla.restaurant.repo;

import org.shivanishukla.restaurant.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product, Long> {
}

