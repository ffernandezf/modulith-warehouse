package com.personal.warehouse.notification.internal;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Notification {
	private Date date;
	private String format;
	private String productName;
}