package com.personal.warehouse.order.business;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.personal.warehouse.customer.CustomerEvents.CustomerFound;
import com.personal.warehouse.customer.CustomerEvents.CustomerNotFound;
import com.personal.warehouse.order.model.Order;
import com.personal.warehouse.order.persistence.OrderRepository;
import com.personal.warehouse.product.ProductEvents.OrderProductFound;
import com.personal.warehouse.product.ProductEvents.OrderProductNotFound;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Transactional
@Service
@RequiredArgsConstructor
public class OrderManagement {

	private Logger LOG = LoggerFactory.getLogger(OrderManagement.class);

	private final OrderRepository orders;

	public List<Order> findAll() {
		return orders.findAll();
	}

	public Optional<Order> find(Long id) {
		return (orders.findById(id));
	}

	public Optional<Order> findByOrderNumber(String orderNb) {
		return (orders.findByOrderNumber(orderNb));
	}

	public Order save(Order o) {
		return (orders.save(o));
	}

	@EventListener
	void onEvent(CustomerFound event) {
		LOG.info("CustomerFound {}", event);

		Optional<Order> orderOptional = findByOrderNumber(event.order().getOrderNumber());
		if (orderOptional.isPresent()) {
			Order o = orderOptional.get();
			o.setCustomer(event.order().getCustomer());
			o.addMessage("Customer Found");
			o.setStatus("CUSTOMER-FOUND");
			orders.save(o);
		}
	}

	@EventListener
	void onEvent(CustomerNotFound event) {
		LOG.info("CustomerNotFound {}", event);

		Optional<Order> orderOptional = findByOrderNumber(event.order().getOrderNumber());
		if (orderOptional.isPresent()) {
			Order o = orderOptional.get();
			o.setCustomer(event.order().getCustomer());
			o.addMessage("Customer NOT Found");
			o.setStatus("TERMINATED");
			orders.save(o);
		}
	}

	@EventListener
	void onEvent(OrderProductFound event) {
		LOG.info("OrderProduct fould {}", event);

		// check status in all the items and set the order status
		Optional<Order> orderOptional = findByOrderNumber(event.order().getOrderNumber());
		if (orderOptional.isPresent()) {
			Order o = orderOptional.get();
			if ("CUSTOMER-FOUND".equals(o.getStatus()))
				o.setStatus("READY");
			orders.save(o);
		}
	}

	@EventListener
	void onEvent(OrderProductNotFound event) {
		LOG.info("OrderProduct fould {}", event);

		// check status in all the items and set the order status
		Optional<Order> orderOptional = findByOrderNumber(event.order().getOrderNumber());
		if (orderOptional.isPresent()) {
			Order o = orderOptional.get();
			o.addMessage(String.format("unknown product in line %d", event.order().getNumItem()));
			o.setStatus("WRONG-LINE-ITEMS");
			orders.save(o);
		}
	}

}
