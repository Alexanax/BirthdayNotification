package com.vk.birthdaynotification;

import com.vk.birthdaynotification.model.Converter;
import com.vk.birthdaynotification.model.members.response.Item;
import com.vk.birthdaynotification.model.members.response.Members;
import com.vk.birthdaynotification.model.members.response.Response;
import com.vk.birthdaynotification.model.sendmessage.response.SendMessage;
import com.vk.birthdaynotification.services.VKService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
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
        SendMessage response = vkService.sendMessageToUsers(items);
        System.out.println(response);
        Assertions.assertNotNull(response);
        Assertions.assertFalse(response.getResponse().stream().anyMatch(r -> r.getError() != null));
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

    @ParameterizedTest
    @CsvSource(value = {
            "3.1, 2023-01-03T00:00+03:00",
            "3.01, 2023-01-03T00:00+03:00",
            "30.1, 2023-01-30T00:00+03:00",
            "30.10, 2023-10-30T00:00+03:00",
            "3.1.2023, 2023-01-03T00:00+03:00",
            "30.10.23, 2023-10-30T00:00+03:00"
    })
    void parseDate(String date, String expectedDate) {
        OffsetDateTime offsetDateTime = Converter.parseDateTimeString(date);
        System.out.println(offsetDateTime);
        Assertions.assertEquals(expectedDate, offsetDateTime.toString());
    }
}
