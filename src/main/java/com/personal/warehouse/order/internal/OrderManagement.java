package com.personal.warehouse.order.internal;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.personal.warehouse.customer.CustomerEvents.CustomerFound;
import com.personal.warehouse.customer.CustomerEvents.CustomerNotFound;

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

}
