package com.personal.warehouse.order;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.personal.warehouse.customer.internal.Customer;
import com.personal.warehouse.customer.internal.CustomerManagement;
import com.personal.warehouse.order.internal.Order;
import com.personal.warehouse.order.internal.OrderManagement;

@RestController
@RequestMapping("/orders")
public class OrderController {

	private final Logger LOG = LoggerFactory.getLogger(OrderController.class);

	private final OrderManagement orderService;
	private final CustomerManagement customerService;

	public OrderController(OrderManagement ordService, CustomerManagement custService) {
		this.orderService = ordService;
		this.customerService = custService;
	}

	@GetMapping
	// curl -X GET http://localhost:8080/orders
	public ResponseEntity<List<Order>> getAllProducts() {
		return ResponseEntity.ok(orderService.findAll());
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	// curl -X POST "http://localhost:8080/orders/add?customerId=1&orderNumber=FFF20240619001"
	public ResponseEntity<Order> createProduct(@RequestParam Long customerId, @RequestParam String orderNb) {
		Optional<Customer> custOptional = customerService.findById(customerId);
		if (custOptional.isPresent()) {
			Customer p = custOptional.get();
			Order o = new Order();
			o.setOrderNumber(orderNb);
			o.setCustomer(p);
			orderService.save(o);
			return ResponseEntity.ok(o);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}