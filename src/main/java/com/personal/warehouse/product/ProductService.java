package com.personal.warehouse.product;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.personal.warehouse.notification.NotificationDTO;
import com.personal.warehouse.notification.NotificationService;
import com.personal.warehouse.notification.internal.NotificationType;
import com.personal.warehouse.product.internal.Product;

@Service
public class ProductService {

	private final NotificationService notificationService;

	public ProductService(NotificationService notificationService) {
		this.notificationService = notificationService;
	}

	public void create(Product product) {
		notificationService.createNotification(new NotificationDTO(new Date(), NotificationType.SMS, product.getName()));
	}
}