package com.personal.warehouse.order.internal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Transactional
@Service
@RequiredArgsConstructor
public class OrderLineManagement {

	private Logger LOG = LoggerFactory.getLogger(OrderLineManagement.class);

	private final OrderLineRepository lines;

	public OrderLine save(OrderLine ol) {
		return (lines.save(ol));
	}

}
