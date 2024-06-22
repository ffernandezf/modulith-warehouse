package com.personal.warehouse.customer;

import org.jmolecules.event.annotation.Externalized;
import org.jmolecules.event.types.DomainEvent;

public class CustomerEvents {

	@Externalized("customer.OrderReceived")
	public record OrderReceived(CustomerOrderDTO order) implements DomainEvent {
	}

	@Externalized("order.CustomerFound")
	public record CustomerFound(CustomerOrderDTO order) implements DomainEvent {
	}

	@Externalized("order.CustomerNotFound")
	public record CustomerNotFound(CustomerOrderDTO order) implements DomainEvent {
	}
}