package com.personal.warehouse.order.internal;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

interface OrderLineRepository extends JpaRepository<OrderLine, Long> {
	Optional<OrderLine> findByOrderIdAndNumItem(Long orderId, int numItem);

}