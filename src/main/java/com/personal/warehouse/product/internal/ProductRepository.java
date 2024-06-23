package com.personal.warehouse.product.internal;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

interface ProductRepository extends JpaRepository<Product, Long> {
	Optional<Product> findByName(String name);
}