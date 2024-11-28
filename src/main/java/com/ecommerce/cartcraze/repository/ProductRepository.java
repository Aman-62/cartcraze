package com.ecommerce.cartcraze.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.cartcraze.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

}
