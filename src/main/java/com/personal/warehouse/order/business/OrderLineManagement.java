package com.personal.warehouse.order.business;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.personal.warehouse.order.model.OrderLine;
import com.personal.warehouse.order.persistence.OrderLineRepository;
import com.personal.warehouse.product.ProductEvents.OrderProductFound;
import com.personal.warehouse.product.ProductEvents.OrderProductNotFound;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Transactional
@Service
@RequiredArgsConstructor
public class OrderLineManagement {

	private Logger LOG = LoggerFactory.getLogger(OrderLineManagement.class);

	private final OrderLineRepository lines;

	public Optional<OrderLine> findById(Long id) {
		return lines.findById(id);
	}

	public OrderLine save(OrderLine ol) {
		return (lines.save(ol));
	}

	@EventListener
	void onEvent(OrderProductFound event) {
		LOG.info("OrderProduct fould {}", event);

		Optional<OrderLine> olOptional = lines.findByOrderIdAndNumItem(event.order().getId(), event.order().getNumItem());
		if (olOptional.isPresent()) {
			OrderLine ol = olOptional.get();
			ol.setProduct(event.order().getProduct());
			ol.setStatus("FOUND-PRODUCT");
			lines.save(ol);
		}
	}

	@EventListener
	void onEvent(OrderProductNotFound event) {
		LOG.info("OrderProduct NOT fould {}", event);

		Optional<OrderLine> olOptional = lines.findByOrderIdAndNumItem(event.order().getId(), event.order().getNumItem());
		if (olOptional.isPresent()) {
			OrderLine ol = olOptional.get();
			ol.setProduct(event.order().getProduct());
			ol.setStatus("UNKNOWN-PRODUCT");
			lines.save(ol);
		}
	}

}
