package com.ecommerce.cartcraze.controller;

import com.ecommerce.cartcraze.model.Review;
import com.ecommerce.cartcraze.model.Product;
import com.ecommerce.cartcraze.model.User;
import com.ecommerce.cartcraze.repository.ReviewRepository;
import com.ecommerce.cartcraze.repository.ProductRepository;
import com.ecommerce.cartcraze.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    // Get all reviews
    @GetMapping
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    // Get reviews by product ID
    @GetMapping("/product/{productId}")
    public Optional<Review> getReviewsByProduct(@PathVariable Integer productId) {
        return reviewRepository.findById(productId);
    }

    // Get reviews by user ID
    @GetMapping("/user/{userId}")
    public Optional<Review> getReviewsByUser(@PathVariable Integer userId) {
        return reviewRepository.findById(userId);
    }

    // Get a review by ID
    @GetMapping("/{id}")
    public Review getReviewById(@PathVariable Integer id) {
        return reviewRepository.findById(id).orElse(null);
    }

    // Create a new review
    @PostMapping
    public Review createReview(@RequestBody Review review) {
        Product product = productRepository.findById(review.getProduct().getId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
        User user = userRepository.findById(review.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        review.setProduct(product);
        review.setUser(user);

        return reviewRepository.save(review);
    }

    // Delete a review by ID
    @DeleteMapping("/{id}")
    public void deleteReview(@PathVariable Integer id) {
        reviewRepository.deleteById(id);
    }

    // Update a review
    @PatchMapping("/{id}")
    public Review updateReview(@PathVariable Integer id, @RequestBody Review reviewDetails) {
        Review review = reviewRepository.findById(id).orElse(null);
        if (review != null) {
            review.setComment(reviewDetails.getComment());
            review.setRating(reviewDetails.getRating());
            Product product = productRepository.findById(reviewDetails.getProduct().getId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            User user = userRepository.findById(reviewDetails.getUser().getId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            review.setProduct(product);
            review.setUser(user);

            return reviewRepository.save(review);
        }
        return null;
    }
}
