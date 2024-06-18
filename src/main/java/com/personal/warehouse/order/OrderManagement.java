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

import org.springframework.stereotype.Service;

import com.personal.warehouse.customer.internal.Customer;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

/**
 * @author Oliver Drotbohm
 */
@Transactional
@Service
@RequiredArgsConstructor
public class OrderManagement {

	private final OrderRepository orders;

	public Order create(Customer cust) {
		return new Order(cust);
	}

	public Order complete(Order order) {
		return orders.save(order.complete());
	}
}
