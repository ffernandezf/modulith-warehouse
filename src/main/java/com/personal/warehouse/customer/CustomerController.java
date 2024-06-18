package com.personal.warehouse.customer;

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

import com.personal.warehouse.customer.internal.Customer;
import com.personal.warehouse.customer.internal.CustomerManagement;

@RestController
@RequestMapping("/customers")
public class CustomerController {

	private final Logger LOG = LoggerFactory.getLogger(CustomerController.class);

	private final CustomerManagement customerService;

	public CustomerController(CustomerManagement custService) {
		this.customerService = custService;
	}

	@GetMapping
	// curl -X GET http://localhost:8080/customers
	public ResponseEntity<List<Customer>> getAllProducts() {
		return ResponseEntity.ok(customerService.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Customer> getProductById(@PathVariable Long id) {
		Optional<Customer> productOptional = customerService.findById(id);
		return productOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	// curl -X POST "http://localhost:8080/customers/add?name=Ericsson"
	public ResponseEntity<Customer> createProduct(@RequestParam String name) {
		LOG.debug("Received attribute name={}", name);
		Customer p = new Customer();
		p.setName(name);
		return ResponseEntity.ok(customerService.save(p));
	}

	@PutMapping("/{id}")
	// curl -X PUT -v -d "{\"id\":1,\"name\":\"Microsoft\"}" -H "Content-Type: application/json" http://localhost:8080/customers/1
	public ResponseEntity<Customer> updateProduct(@PathVariable Long id, @RequestBody Customer productDetails) {
		Optional<Customer> productOptional = customerService.findById(id);
		if (productOptional.isPresent()) {
			Customer Customer = productOptional.get();
			Customer.setName(productDetails.getName());
			return ResponseEntity.ok(customerService.save(Customer));
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
		customerService.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}