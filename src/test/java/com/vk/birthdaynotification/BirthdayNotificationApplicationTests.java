package com.vk.birthdaynotification;

import com.vk.birthdaynotification.dto.members.response.Item;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
class BirthdayNotificationApplicationTests {
	@Autowired
	private com.vk.birthdaynotification.services.VKService vkService;

	@Test
	void sendMessageToUsers() {
		List<Item> items = vkService.getMembers()
				.getResponse()
				.getItems()
				.stream()
				.filter(item -> item.getFirstName().equals("Александр") && item.getLastName().equals("Максимов"))
				.collect(Collectors.toList());
		String response = vkService.sendMessageToUsers(items);
		System.out.println(response);
		Assertions.assertFalse(response.contains("error"));
	}
}
