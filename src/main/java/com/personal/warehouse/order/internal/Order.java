package com.personal.warehouse.order.internal;

import java.util.List;

import org.springframework.modulith.NamedInterface;

import com.personal.warehouse.product.internal.Product;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode.Include;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "CUSTOMER_ORDERS")
// Mandatory in conjunction with JPA: an equal based on fields is not desired
@lombok.EqualsAndHashCode(onlyExplicitlyIncluded = true)
// Mandatory in conjunction with JPA: force is needed to generate default values for final fields, that will be overriden by JPA
@lombok.NoArgsConstructor(force = true)
@Getter
@Setter
// say to Modulith that this class is exposed to other modules
@NamedInterface
public class Order {

	@Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String orderNumber;

	@OneToMany
	private List<OrderLine> lines;
}

@Entity
@Table(name = "CUSTOMER_ORDERS_LINES")
@Getter
@Setter
@lombok.NoArgsConstructor
@lombok.EqualsAndHashCode(onlyExplicitlyIncluded = true)
class OrderLine {
	@Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private Product product;

	private int quantity;
	private double price;
	private double totalPrice;

}