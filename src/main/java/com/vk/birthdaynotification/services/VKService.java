package com.vk.birthdaynotification.services;

import com.vk.birthdaynotification.client.VKClient;

import com.vk.birthdaynotification.model.Converter;
import com.vk.birthdaynotification.model.members.response.Members;
import com.vk.birthdaynotification.model.members.response.Item;
import com.vk.birthdaynotification.model.sendmessage.response.SendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class VKService {
    private final VKClient vkClient;

    @Value("${vk.group.id}")
    private String groupId;

    @Value("${vk.api.accessToken}")
    private String accessToken;

    @Value("${vk.api.version}")
    private String version;

    @Value("${notification.daysBefore}")
    private Integer daysBeforeBirthday;

    @Value("${notification.message}")
    private String message;

    @Autowired
    public VKService(VKClient vkClient) {
        this.vkClient = vkClient;
    }

    public Members getMembers() {
        return vkClient.getMembers(
                groupId,
                accessToken,
                "bdate",
                version
        );
    }

    public List<Item> sortedMembersToBirthDate(Members members) {
        List<Item> membersForSendingNotification = new ArrayList<>();

        for (Item member : members.getResponse().getItems().stream().filter(i -> i.getBdate() != null).collect(Collectors.toList())) {
            OffsetDateTime offsetDateTime = Converter.parseDateTimeString(member.getBdate());
            if (LocalDate.now().plusDays(daysBeforeBirthday).equals(offsetDateTime.toLocalDate())) {
                membersForSendingNotification.add(member);
            }
        }
        System.out.println("Отобраны пользователи для отправки уведомления : " + membersForSendingNotification);
        return membersForSendingNotification;
    }

    public SendMessage sendMessageToUsers(List<Item> usersForSendingMessage) {
        var messageResponse = vkClient.sendMessages(
                getIdsUsers(usersForSendingMessage),
                getRandomId(),
                accessToken,
                message,
                version
        );
        if (messageHasError(messageResponse)) {
            System.out.println("Не смог отправить уведомление пользователю");
            alertAdmin(usersForSendingMessage);
        }
        return messageResponse;
    }

    private void alertAdmin(List<Item> usersWhoHaveNotReceivedMessage) {
        vkClient.sendMessages(
                List.of(60528638L),
                getRandomId(),
                accessToken,
                "Сообщение не доставлено пользователям : " +
                        usersWhoHaveNotReceivedMessage.stream().map(item -> "\n" + item.getLastName() + " "
                                + item.getFirstName() + " "
                                + item.getBdate() + "\n"
                                + "https://vk.com/id" + item.getId()).sorted().collect(Collectors.joining()),
                version
        );
        System.out.println("Отправил сообщение админу о том, что пользователь не получил сообщение");
    }

    private static boolean messageHasError(SendMessage message) {
        return message.getResponse().stream().anyMatch(r -> r.getError() == null);
    }

    private static List<Long> getIdsUsers(List<Item> usersForSendingMessage) {
        return usersForSendingMessage.stream().map(Item::getId).collect(Collectors.toList());
    }

    private static Integer getRandomId() {
        return ((int) (Math.random() * 2147483647));
    }
}
