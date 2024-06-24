package com.personal.warehouse.order.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.personal.warehouse.customer.model.Customer;

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
@lombok.ToString
@Getter
@Setter
public class Order {

	@Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	private Customer customer;

	private Long receivedCustomerId;
	private String orderNumber;
	private String status = "OPEN";

	@OneToMany(mappedBy = "order")
	@JsonManagedReference // includes those fields in the generated json
	private List<OrderLine> lines = new ArrayList<>();

	private String statusMessages;

	Order complete() {

		this.status = "COMPLETED";
		return this;
	}

	public OrderLine addLine(String pNumber, int quantity) {
		OrderLine line = new OrderLine();
		line.setOrder(this);
		line.setProductNumber(pNumber);
		line.setQuantity(quantity);
		line.setNumItem(lines.size() + 1);
		line.setStatus("CHECKING-PRODUCT");
		this.lines.add(line);
		return line;
	}

	public Integer getLastLineItem() {
		if (lines.isEmpty())
			return null;
		else
			return (lines.get(lines.size() - 1).getNumItem());
	}

	public Order addMessage(String msg) {
		StringBuilder aux = new StringBuilder();
		if (null != statusMessages)
			aux.append(statusMessages).append(";");
		aux.append(msg);
		statusMessages = aux.toString();
		return this;
	}

}
