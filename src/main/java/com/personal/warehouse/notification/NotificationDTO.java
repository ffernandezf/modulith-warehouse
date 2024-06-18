package com.personal.warehouse.notification;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class NotificationDTO {
	private Date date;
	private String format;
	private String productName;
}