package com.personal.warehouse.product;

import org.jmolecules.event.annotation.Externalized;
import org.jmolecules.event.types.DomainEvent;

public class ProductEvents {

	@Externalized("customer.OrderProductRequested")
	public record OrderProductRequested(OrderProductDTO order) implements DomainEvent {
	}

	@Externalized("customer.OrderProductFound")
	public record OrderProductFound(OrderProductDTO order) implements DomainEvent {
	}

	@Externalized("customer.OrderProductNotFound")
	public record OrderProductNotFound(OrderProductDTO order) implements DomainEvent {
	}

}