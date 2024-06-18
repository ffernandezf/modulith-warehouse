package com.personal.warehouse.customer.internal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode.Include;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "CUSTOMER")
// Mandatory in conjunction with JPA: an equal based on fields is not desired
@lombok.EqualsAndHashCode(onlyExplicitlyIncluded = true)
// Mandatory in conjunction with JPA: force is needed to generate default values for final fields, that will be overriden by JPA
@lombok.NoArgsConstructor(force = true)
@Getter
@Setter

public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Include
	private Long id;
	private String name;
}