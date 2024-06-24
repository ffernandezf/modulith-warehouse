package com.personal.warehouse.order.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.personal.warehouse.product.model.Product;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode.Include;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "CUSTOMER_ORDERS_LINES")
@Getter
@Setter
@lombok.NoArgsConstructor
@lombok.EqualsAndHashCode(onlyExplicitlyIncluded = true)
@lombok.ToString
public class OrderLine {
	@Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private int numItem;

	@ManyToOne
	private Product product;

	@ManyToOne
	@JoinColumn(name = "order_id")
	@JsonBackReference // to not include this field in the generated JSON (avoid recursive loop)
	private Order order;

	private String productNumber;
	private int quantity;
	private String status;
	private double price;
	private double totalPrice;

}