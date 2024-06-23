package com.personal.warehouse.product;

import org.springframework.stereotype.Service;

import com.personal.warehouse.product.internal.Product;
import com.personal.warehouse.product.internal.ProductManagement;

@Service
public class ProductService {

	private final ProductManagement productService;

	public ProductService(ProductManagement productService) {
		this.productService = productService;
	}

	public void create(Product product) {
		productService.create(product.getName(), product.getQuantity(), product.getPrice());
	}
}