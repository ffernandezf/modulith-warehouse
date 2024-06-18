package com.personal.warehouse.product.internal;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Transactional
@Service
@RequiredArgsConstructor
public class ProductManagement {

	private final ProductRepository products;

	public Product create(String name, int quantity, double price) {
		Product p = new Product();
		p.setName(name);
		p.setQuantity(quantity);
		p.setPrice(price);
		return p;
	}

	public Product save(Product p) {
		return products.save(p);
	}

	public List<Product> findAll() {
		return products.findAll();
	}

	public Optional<Product> findById(Long id) {
		return products.findById(id);
	}

	public void deleteById(Long id) {
		products.deleteById(id);
	}
}