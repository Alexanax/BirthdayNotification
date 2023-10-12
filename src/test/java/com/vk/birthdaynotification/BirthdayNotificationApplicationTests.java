package com.vk.birthdaynotification;

import com.vk.birthdaynotification.core.CoreLogic;
import com.vk.birthdaynotification.model.Converter;
import com.vk.birthdaynotification.model.members.response.Item;
import com.vk.birthdaynotification.model.members.response.Members;
import com.vk.birthdaynotification.model.members.response.Response;
import com.vk.birthdaynotification.model.messages.conversations.response.*;
import com.vk.birthdaynotification.model.messages.sendmessage.response.SendMessage;
import com.vk.birthdaynotification.services.VKService;
import org.apache.logging.log4j.core.Core;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
class BirthdayNotificationApplicationTests {
    private VKService vkService;
    private CoreLogic coreLogic;

    @Autowired
    BirthdayNotificationApplicationTests(VKService vkService, CoreLogic coreLogic) {
        this.vkService = vkService;
        this.coreLogic = coreLogic;
    }

    @Test
    void sendMessageToUsersTest() {
        Item item = new Item();
        item.setId(152543059L);
        item.setFirstName("Александр");
        item.setLastName("Максимов");
        item.setCanAccessClosed(true);
        item.setIsClosed(false);
        item.setBdate("13.2");
        List<Item> items = new ArrayList<>();
        items.add(item);
        SendMessage response = vkService.sendMessageToUsersMembers(items);
        System.out.println(response);
        Assertions.assertNotNull(response);
        Assertions.assertFalse(response.getResponse().stream().anyMatch(r -> r.getError() != null));
    }


    @Test
    void getMembersTest() {
        List<Item> items = vkService.getMembers()
                .getResponse()
                .getItems();
        System.out.println(items);
        Assertions.assertNotNull(items);
    }

    @Test
    void sortedItemsByBirthdayTest() {
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
    void parseDateTest(String date, String expectedDate) {
        OffsetDateTime offsetDateTime = Converter.parseDateTimeString(date);
        System.out.println(offsetDateTime);
        Assertions.assertEquals(expectedDate, offsetDateTime.toString());
    }

    @Test
    void getConversationTest() {
        ResponseConversation conversations = vkService.getConversations();
        System.out.println(conversations);
        Assertions.assertNotNull(conversations);
    }

    @Test
    void sortConversationTest() {
        LocalDate dateOfBirthday = LocalDate.now().minusYears(25).plusDays(21);
        LocalDate dateOfBirthday2 = LocalDate.now().minusYears(20).minusMonths(2);
        Profile profile = new Profile();
        Profile profile2 = new Profile();
        profile.setBdate(dateOfBirthday.getDayOfMonth() + "." + dateOfBirthday.getMonthValue() + "." + dateOfBirthday.getYear());
        profile2.setBdate(dateOfBirthday2.getDayOfMonth() + "." + dateOfBirthday2.getMonthValue() + "." + dateOfBirthday2.getYear());
        var sortedConversation = coreLogic.sortedConversationToBirthDate(new ResponseConversation(
                new com.vk.birthdaynotification.model.messages.conversations.response.Response(
                        1,
                        List.of(new com.vk.birthdaynotification.model.messages.conversations.response.Item()),
                        List.of(profile),
                        List.of(new Group())
                )
        ));
        System.out.println(sortedConversation);
        Assertions.assertEquals(1, sortedConversation.size());
    }
}
