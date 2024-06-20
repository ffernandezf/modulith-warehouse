package com.personal.warehouse.customer;

import lombok.EqualsAndHashCode.Include;
import lombok.Getter;
import lombok.Setter;

@lombok.EqualsAndHashCode(onlyExplicitlyIncluded = true)
// Mandatory in conjunction with JPA: force is needed to generate default values for final fields, that will be overriden by JPA
@lombok.NoArgsConstructor(force = true)
@Getter
@Setter
public class CustomerOrderDTO {

	@Include
	private Long id;
	private Long receivedCustomerId;
	private String orderNumber;
	private String status = "OPEN";
}
