package com.vk.birthdaynotification;

import com.vk.birthdaynotification.model.Converter;
import com.vk.birthdaynotification.model.members.response.Item;
import com.vk.birthdaynotification.model.members.response.Members;
import com.vk.birthdaynotification.model.members.response.Response;
import com.vk.birthdaynotification.services.VKService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@SpringBootTest
class BirthdayNotificationApplicationTests {
	private VKService vkService;

	@Autowired
	BirthdayNotificationApplicationTests(VKService vkService) {
		this.vkService = vkService;
	}

	@Test
	void sendMessageToUsers() {
		Item item = new Item();
		item.setId(152543059L);
		item.setFirstName("Александр");
		item.setLastName("Максимов");
		item.setCanAccessClosed(true);
		item.setIsClosed(false);
		item.setBdate("13.2");
		List<Item> items = new ArrayList<>();
		items.add(item);
		ResponseEntity<String> response = vkService.sendMessageToUsers(items);
		System.out.println(response.getBody());
		Assertions.assertFalse(Objects.requireNonNull(response.getBody()).contains("error"));
	}


	@Test
	void getMembers() {
		List<Item> items = vkService.getMembers()
				.getResponse()
				.getItems();
		System.out.println(items);
		Assertions.assertNotNull(items);
	}

	@Test
	void sortedItemsByBirthday() {
		LocalDate dateOfBirthday = LocalDate.now().plusDays(21);
		Members members = new Members();
		Response response = new Response();
		Item item = new Item();
		item.setId(152543059L);
		item.setFirstName("Александр");
		item.setLastName("Максимов");
		item.setCanAccessClosed(true);
		item.setIsClosed(false);
		item.setBdate(dateOfBirthday.getDayOfMonth() + "." + dateOfBirthday.getMonthValue() + "." + dateOfBirthday.getYear());
		List<Item> items = new ArrayList<>();
		items.add(item);
		response.setItems(items);
		members.setResponse(response);
		List<Item> membersForSendingNotification = new ArrayList<>();

		for (Item member : members.getResponse().getItems().stream().filter(i -> i.getBdate() != null).collect(Collectors.toList())) {
			OffsetDateTime offsetDateTime = Converter.parseDateTimeString(member.getBdate());
			if (LocalDate.now().plusDays(21).equals(offsetDateTime.toLocalDate())) {
				membersForSendingNotification.add(member);
			}
		}
		System.out.println(membersForSendingNotification);
		Assertions.assertNotEquals(0, membersForSendingNotification.size());
	}
}
