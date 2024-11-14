package org.shivanishukla.restaurant.controller;

import lombok.RequiredArgsConstructor;
import org.shivanishukla.restaurant.dto.ProductRequest;
import org.shivanishukla.restaurant.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

}
