package com.personal.warehouse.order.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.personal.warehouse.order.model.OrderLine;

public interface OrderLineRepository extends JpaRepository<OrderLine, Long> {
	Optional<OrderLine> findByOrderIdAndNumItem(Long orderId, int numItem);

}