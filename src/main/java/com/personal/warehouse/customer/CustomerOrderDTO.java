package com.personal.warehouse.customer;

import lombok.EqualsAndHashCode.Include;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@lombok.EqualsAndHashCode(onlyExplicitlyIncluded = true)
// Mandatory in conjunction with JPA: force is needed to generate default values for final fields, that will be overriden by JPA
@NoArgsConstructor(force = true)
@ToString
@Getter
@Setter
public class CustomerOrderDTO {

	@Include
	private Long id;
	private Long receivedCustomerId;
	private String orderNumber;
	private String status = "OPEN";
}
