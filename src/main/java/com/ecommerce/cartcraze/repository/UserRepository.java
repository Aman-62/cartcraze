package com.ecommerce.cartcraze.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.cartcraze.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

}
