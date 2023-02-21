package com.vk.birthdaynotification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class BirthdayNotificationApplication {

	public static void main(String[] args) {
		SpringApplication.run(BirthdayNotificationApplication.class, args);
	}

}
