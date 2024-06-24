package com.personal.warehouse.customer;

import org.jmolecules.event.annotation.Externalized;
import org.jmolecules.event.types.DomainEvent;

public class CustomerEvents {

	@Externalized("customer.OrderReceived") // para utilizar broker externos
	public record OrderReceived(CustomerOrderDTO order) implements DomainEvent {
	}

	@Externalized("order.CustomerFound") // para utilizar broker externos
	public record CustomerFound(CustomerOrderDTO order) implements DomainEvent {
	}

	@Externalized("order.CustomerNotFound") // para utilizar broker externos
	public record CustomerNotFound(CustomerOrderDTO order) implements DomainEvent {
	}
}