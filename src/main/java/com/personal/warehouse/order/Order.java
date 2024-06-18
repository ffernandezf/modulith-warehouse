/*
 * Copyright 2022-2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package com.personal.warehouse.order;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.personal.warehouse.customer.internal.Customer;
import com.personal.warehouse.product.internal.Product;

import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Oliver Drotbohm
 */
@Getter
@Setter
@Table(name = "ORDER")
public class Order {

	private long id;
	private final Customer customer;
	private final List<LineItem> lineItems = new ArrayList<>();

	private Status status;

	public Order(Customer cust) {
		this.id = UUID.randomUUID().getLeastSignificantBits();
		this.status = Status.OPEN;
		this.customer = cust;
	}

	Order complete() {
		this.status = Status.COMPLETED;
		return this;
	}

	Order add(LineItem item) {

		this.lineItems.add(item);

		return this;
	}

	enum Status {
		OPEN, COMPLETED, CANCELLED;
	}

	@Getter
	static class LineItem {

		private int line;
		private Product product;
		private double amount;

		LineItem(int line, Product prod, long amount) {

			this.line = line;
			this.product = prod;
			this.amount = amount;
		}
	}
}
