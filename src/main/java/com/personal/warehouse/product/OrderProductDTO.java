package com.personal.warehouse.product;

import lombok.EqualsAndHashCode.Include;

import com.personal.warehouse.product.model.Product;

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
public class OrderProductDTO {

	@Include
	private Long id;
	private String orderNumber;
	private int numItem;
	private Product product;
	private String productName;
	private int quantity;

}
