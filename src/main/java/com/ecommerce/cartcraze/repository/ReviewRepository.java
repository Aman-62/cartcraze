package com.ecommerce.cartcraze.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.cartcraze.model.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

}
