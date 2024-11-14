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
    public String deleteProduct(Long productId) {
        if (productRepo.existsById(productId)) {
            productRepo.deleteById(productId);
            return "Product deleted successfully";
        } else {
            throw new RuntimeException("Product not found");
        }
    }

    public Product getProductById(Long productId) {
        return productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }


    public String updateProduct(Long productId, ProductRequest updatedProduct) {
        Product existingProduct = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        existingProduct.setProductName(updatedProduct.productName());
        existingProduct.setProductPrice(updatedProduct.productPrice());

        productRepo.save(existingProduct);
        return "Product updated successfully";
    }

}
