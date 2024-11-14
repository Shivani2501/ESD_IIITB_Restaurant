package org.shivanishukla.restaurant.controller;

import lombok.RequiredArgsConstructor;
import org.shivanishukla.restaurant.dto.ProductRequest;
import org.shivanishukla.restaurant.entity.Product;
import org.shivanishukla.restaurant.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor

public class ProductController {
    private final ProductService productService;

    @PostMapping("/create")
    public ResponseEntity<String> createProduct(@RequestBody @Valid ProductRequest request) {
        String response = productService.createProduct(request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long productId) {
        try {
            String result = productService.deleteProduct(productId);
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable Long productId) {
        try {
            Product product = productService.getProductById(productId);
            return ResponseEntity.ok(product);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @PutMapping("/update/{productId}")
    public ResponseEntity<String> updateProduct(@PathVariable Long productId, @RequestBody ProductRequest updatedProduct) {
        try {
            String result = productService.updateProduct(productId, updatedProduct);
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @GetMapping("/top-products")
    public ResponseEntity<List<Product>> getTop2ProductsByPriceRange(
            @RequestParam("minPrice") Double minPrice,
            @RequestParam("maxPrice") Double maxPrice) {
        List<Product> products = productService.getTop2ProductsByPriceRange(minPrice, maxPrice);
        if (products.isEmpty()) {
            return ResponseEntity.status(404).body(null);
        }
        return ResponseEntity.ok(products);
    }


}
