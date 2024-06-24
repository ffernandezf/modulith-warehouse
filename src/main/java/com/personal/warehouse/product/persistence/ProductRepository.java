package com.personal.warehouse.product.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.personal.warehouse.product.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	Optional<Product> findByName(String name);
}