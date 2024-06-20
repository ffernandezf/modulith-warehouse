package com.personal.warehouse.order;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.personal.warehouse.customer.CustomerEvents;
import com.personal.warehouse.customer.CustomerOrderDTO;
import com.personal.warehouse.order.internal.Order;
import com.personal.warehouse.order.internal.OrderManagement;

@RestController
@RequestMapping("/orders")
public class OrderController {

	private final Logger LOG = LoggerFactory.getLogger(OrderController.class);

	private final OrderManagement orderService;
	private final ApplicationEventPublisher events;

	public OrderController(OrderManagement ordService, ApplicationEventPublisher events) {
		this.orderService = ordService;
		this.events = events;
	}

	@GetMapping
	// curl -X GET http://localhost:8080/orders
	public ResponseEntity<List<Order>> getAllOrders() {
		return ResponseEntity.ok(orderService.findAll());
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	// curl -X POST "http://localhost:8080/orders/add?customerId=1&orderNb=FFF20240619001"
	public ResponseEntity<Order> createOrder(@RequestParam Long customerId, @RequestParam String orderNb) {
		Order o = new Order();
		o.setOrderNumber(orderNb);
		o.setReceivedCustomerId(customerId);
		orderService.save(o);
		CustomerOrderDTO odto = new CustomerOrderDTO();
		odto.setOrderNumber(o.getOrderNumber());
		odto.setReceivedCustomerId(o.getReceivedCustomerId());
		events.publishEvent(new CustomerEvents.OrderReceived(odto));
		return ResponseEntity.ok(o);
		// Optional<Customer> custOptional = customerService.findById(customerId);
		// if (custOptional.isPresent()) {
		// Customer p = custOptional.get();
		// o.setCustomer(p);
		// orderService.save(o);
		// return ResponseEntity.ok(o);
		// } else {
		// return ResponseEntity.notFound().build();
		// }
	}
}