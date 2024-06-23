package com.personal.warehouse.order.internal;

import org.springframework.data.jpa.repository.JpaRepository;

interface OrderLineRepository extends JpaRepository<OrderLine, Long> {
}