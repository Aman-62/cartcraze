package com.ecommerce.cartcraze.controller;

import com.ecommerce.cartcraze.model.Product;
import com.ecommerce.cartcraze.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        if (product == null) {
            throw new NullPointerException("Product cannot be null");
        }

        try {
            return productRepository.save(product);
        } catch (Exception e) {
            throw new RuntimeException("Error while saving product", e);
        }
    }

    @GetMapping("/{productId}")
    public Product getProductById(@PathVariable Integer productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer id, @RequestBody Product updatedProduct) {
        if (updatedProduct == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Updated product cannot be null");
        }
        try {
            Product foundProduct = productRepository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
            foundProduct.setName(updatedProduct.getName());
            foundProduct.setPrice(updatedProduct.getPrice());
            foundProduct.setDescription(updatedProduct.getDescription());
            Product savedProduct = productRepository.save(foundProduct);
            return ResponseEntity.ok(savedProduct);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while updating product", e);
        }
    }

}
