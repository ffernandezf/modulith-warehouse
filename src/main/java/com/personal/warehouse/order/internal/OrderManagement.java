package com.personal.warehouse.order.internal;

import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Transactional
@Service
@RequiredArgsConstructor
public class OrderManagement {

	private final OrderRepository orders;

	public List<Order> findAll() {
		return orders.findAll();
	}

	public Order save(Order o) {
		return (orders.save(o));
	}

}
