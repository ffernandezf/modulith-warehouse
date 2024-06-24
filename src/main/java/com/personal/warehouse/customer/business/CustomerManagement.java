package com.personal.warehouse.customer.business;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.personal.warehouse.customer.CustomerEvents;
import com.personal.warehouse.customer.CustomerEvents.OrderReceived;
import com.personal.warehouse.customer.model.Customer;
import com.personal.warehouse.customer.persistence.CustomerRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Transactional
@Service
@RequiredArgsConstructor
public class CustomerManagement {

	private Logger LOG = LoggerFactory.getLogger(CustomerManagement.class);

	private final CustomerRepository customers;
	private final ApplicationEventPublisher events;

	public Customer create(String name) {
		Customer c = new Customer();
		c.setName(name);
		return c;
	}

	public Customer save(Customer p) {
		return customers.save(p);
	}

	public List<Customer> findAll() {
		return customers.findAll();
	}

	public Optional<Customer> findById(Long id) {
		return customers.findById(id);
	}

	public void deleteById(Long id) {
		customers.deleteById(id);
	}

	@EventListener
	void onEvent(OrderReceived event) {
		LOG.info("new Order received {}", event);
		Optional<Customer> custOptional = findById(event.order().getReceivedCustomerId());
		if (custOptional.isPresent()) {
			Customer p = custOptional.get();
			event.order().setCustomer(p);
			events.publishEvent(new CustomerEvents.CustomerFound(event.order()));
		} else {
			events.publishEvent(new CustomerEvents.CustomerNotFound(event.order()));
		}
	}
}