package com.personal.warehouse.customer.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.personal.warehouse.customer.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}