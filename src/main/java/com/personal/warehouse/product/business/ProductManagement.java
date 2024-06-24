package com.personal.warehouse.product.business;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.personal.warehouse.product.ProductEvents;
import com.personal.warehouse.product.ProductEvents.OrderProductRequested;
import com.personal.warehouse.product.model.Product;
import com.personal.warehouse.product.persistence.ProductRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Transactional
@Service
@RequiredArgsConstructor
public class ProductManagement {

	private Logger LOG = LoggerFactory.getLogger(ProductManagement.class);

	private final ApplicationEventPublisher events;

	private final ProductRepository products;

	public Product create(String name, int quantity, double price) {
		Product p = new Product();
		p.setName(name);
		p.setQuantity(quantity);
		p.setPrice(price);
		return p;
	}

	public Product save(Product p) {
		return products.save(p);
	}

	public List<Product> findAll() {
		return products.findAll();
	}

	public Optional<Product> findById(Long id) {
		return products.findById(id);
	}

	public Optional<Product> findByName(String name) {
		return products.findByName(name);
	}

	public void deleteById(Long id) {
		products.deleteById(id);
	}

	@EventListener
	void onEvent(OrderProductRequested event) {
		LOG.info("new OrderProductRequested received {}", event);
		Optional<Product> pOptional = findByName(event.order().getProductName());
		if (pOptional.isPresent()) {
			Product p = pOptional.get();
			event.order().setProduct(p);
			events.publishEvent(new ProductEvents.OrderProductFound(event.order()));
		} else {
			events.publishEvent(new ProductEvents.OrderProductNotFound(event.order()));
		}
	}

}