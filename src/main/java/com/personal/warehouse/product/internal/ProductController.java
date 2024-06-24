package com.personal.warehouse.product.internal;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {

	private final Logger LOG = LoggerFactory.getLogger(ProductController.class);

	private final ProductManagement productService;

	public ProductController(ProductManagement productService) {
		this.productService = productService;
	}

	@GetMapping
	// curl -X GET http://localhost:8080/products
	public ResponseEntity<List<Product>> getAllProducts() {
		return ResponseEntity.ok(productService.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable Long id) {
		Optional<Product> productOptional = productService.findById(id);
		return productOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	// curl -X POST "http://localhost:8080/products/add?name=secret&qtty=1&value=12.25"
	public ResponseEntity<Product> createProduct(@RequestParam String name, @RequestParam int qtty, @RequestParam double value) {
		LOG.debug("Received attribute name={}", name);
		Product p = new Product();
		p.setName(name);
		p.setQuantity(qtty);
		p.setPrice(value);
		return ResponseEntity.ok(productService.save(p));
	}

	@PutMapping("/{id}")
	// curl -X PUT -v -d "{\"id\":1,\"name\":\"MTU00123432\",\"quantity\":5,\"price\":1258.254}" -H "Content-Type: application/json" http://localhost:8080/products/1
	public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product productDetails) {
		Optional<Product> productOptional = productService.findById(id);
		if (productOptional.isPresent()) {
			Product product = productOptional.get();
			product.setName(productDetails.getName());
			product.setQuantity(productDetails.getQuantity());
			product.setPrice(productDetails.getPrice());
			return ResponseEntity.ok(productService.save(product));
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
		productService.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}