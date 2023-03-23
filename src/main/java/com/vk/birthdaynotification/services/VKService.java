package com.vk.birthdaynotification.services;

import com.vk.birthdaynotification.client.VKClient;

import com.vk.birthdaynotification.model.members.Converter;
import com.vk.birthdaynotification.model.members.response.Members;
import com.vk.birthdaynotification.model.members.response.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
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
        return membersForSendingNotification;
    }

    public ResponseEntity<String> sendMessageToUsers(List<Item> users) {
        return vkClient.sendMessages(
                users.stream().map(Item::getId).collect(Collectors.toList()),
                ((int) (Math.random() * 2147483647)),
                accessToken,
                message,
                version
        );
    }
}
