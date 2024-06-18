package com.personal.warehouse.customer.internal;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Transactional
@Service
@RequiredArgsConstructor
public class CustomerManagement {

	private final CustomerRepository customers;

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
}