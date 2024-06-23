package com.personal.warehouse.order;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.personal.warehouse.customer.CustomerEvents;
import com.personal.warehouse.customer.CustomerOrderDTO;
import com.personal.warehouse.order.internal.Order;
import com.personal.warehouse.order.internal.OrderLineManagement;
import com.personal.warehouse.order.internal.OrderManagement;
import com.personal.warehouse.product.OrderProductDTO;
import com.personal.warehouse.product.ProductEvents.OrderProductRequested;

@RestController
@RequestMapping("/orders")
public class OrderController {

	private final Logger LOG = LoggerFactory.getLogger(OrderController.class);

	private final OrderManagement orderService;
	private final OrderLineManagement orderLineService;
	private final ApplicationEventPublisher events;

	public OrderController(OrderManagement ordService, OrderLineManagement orderLineService, ApplicationEventPublisher events) {
		this.orderService = ordService;
		this.orderLineService = orderLineService;
		this.events = events;
	}

	@GetMapping
	// curl -X GET http://localhost:8080/orders
	public ResponseEntity<List<Order>> getAllOrders() {
		return ResponseEntity.ok(orderService.findAll());
	}

	@GetMapping("/{orderNumber}")
	// curl -X GET http://localhost:8080/orders/1
	public ResponseEntity<Order> getOrderByOrderNumber(@PathVariable String orderNumber) {
		Optional<Order> oOptional = orderService.findByOrderNumber(orderNumber);
		return oOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@RequestMapping(value = "/{orderNumber}/addProduct")
	// curl -X POST "http://localhost:8080/orders/1/addProduct?productNumber=switchboard001&quantity=2"
	public ResponseEntity<OrderDTO> addProduct(@PathVariable String orderNumber, @RequestParam String productNumber, @RequestParam int quantity) {
		Optional<Order> oOptional = orderService.findByOrderNumber(orderNumber);
		if (oOptional.isPresent()) {
			Order o = oOptional.get();
			orderLineService.save(o.addLine(productNumber, quantity));
			orderService.save(o);

			OrderDTO odto = new OrderDTO();
			odto.setId(o.getId());
			odto.setOrderNumber(o.getOrderNumber());
			odto.setCustomer(o.getCustomer());
			odto.setReceivedCustomerId(o.getReceivedCustomerId());
			odto.setMessage("product request received. Working on it");

			OrderProductDTO opDto = new OrderProductDTO();
			opDto.setId(o.getId());
			opDto.setOrderNumber(o.getOrderNumber());
			opDto.setProductName(productNumber);
			opDto.setNumItem(o.getLastLineItem());
			opDto.setQuantity(quantity);
			events.publishEvent(new OrderProductRequested(opDto));

			return ResponseEntity.ok(odto);
		} else {
			OrderDTO odto = new OrderDTO();
			odto.setOrderNumber(orderNumber);
			odto.setMessage("Order not found. Product cannot be added.");
			return new ResponseEntity<OrderDTO>(odto, HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	// curl -X POST "http://localhost:8080/orders/add?customerId=1&orderNb=FFF20240619001"
	public ResponseEntity<OrderDTO> createOrder(@RequestParam Long customerId, @RequestParam String orderNb) {
		Order o = new Order();
		o.setOrderNumber(orderNb);
		o.setReceivedCustomerId(customerId);
		o.addMessage("Order received. Being processed");
		Order savedOrder = orderService.save(o);

		// publish the event to find the customer
		CustomerOrderDTO cdto = new CustomerOrderDTO();
		cdto.setOrderNumber(savedOrder.getOrderNumber());
		cdto.setReceivedCustomerId(savedOrder.getReceivedCustomerId());
		events.publishEvent(new CustomerEvents.OrderReceived(cdto));

		// responds to the client
		OrderDTO odto = new OrderDTO();
		odto.setId(savedOrder.getId());
		odto.setOrderNumber(savedOrder.getOrderNumber());
		odto.setReceivedCustomerId(savedOrder.getReceivedCustomerId());
		odto.setMessage("Order received. Please track the status to check the progress");
		return ResponseEntity.ok(odto);
	}
}