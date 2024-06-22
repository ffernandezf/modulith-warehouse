package com.personal.warehouse.order;

import com.personal.warehouse.customer.internal.Customer;

import lombok.EqualsAndHashCode.Include;
import lombok.Getter;
import lombok.Setter;

@lombok.EqualsAndHashCode(onlyExplicitlyIncluded = true)
// Mandatory in conjunction with JPA: force is needed to generate default values for final fields, that will be overriden by JPA
@lombok.NoArgsConstructor(force = true)
@Getter
@Setter
public class OrderDTO {

	@Include
	private Long id;
	private Customer customer;
	private Long receivedCustomerId;
	private String orderNumber;
	private String status;
	private String message;
}