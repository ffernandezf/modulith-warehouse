package com.personal.warehouse.notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

	private static final Logger LOG = LoggerFactory.getLogger(NotificationService.class);

	@Async
	@ApplicationModuleListener
	public void createNotification(NotificationDTO notificationDTO) {
		LOG.info("Received notification by module dependency for product {} in date {} by {}.", notificationDTO.getProductName(), notificationDTO.getDate(),
				notificationDTO.getFormat());
	}
}