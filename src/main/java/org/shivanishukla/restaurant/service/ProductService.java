package org.shivanishukla.restaurant.service;

import lombok.RequiredArgsConstructor;
import org.shivanishukla.restaurant.dto.ProductRequest;
import org.shivanishukla.restaurant.entity.Product;
import org.shivanishukla.restaurant.repo.ProductRepo;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepo productRepo;

    public String createProduct(ProductRequest request) {
        Product product = Product.builder()
                .productName(request.productName())
                .productPrice(request.productPrice())
                .build();

        productRepo.save(product);
        return "Product created successfully";
    }
}
