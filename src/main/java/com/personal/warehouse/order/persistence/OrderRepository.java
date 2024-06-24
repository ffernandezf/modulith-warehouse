package com.personal.warehouse.order.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.personal.warehouse.order.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
	Optional<Order> findByOrderNumber(String orderNumber);
}