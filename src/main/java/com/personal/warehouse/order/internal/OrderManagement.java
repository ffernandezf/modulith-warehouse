package com.personal.warehouse.order.internal;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Transactional
@Service
@RequiredArgsConstructor
public class OrderManagement {

	private final OrderRepository orders;

}
