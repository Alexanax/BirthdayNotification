package com.vk.birthdaynotification.services;

import com.vk.birthdaynotification.client.VKClient;

import com.vk.birthdaynotification.dto.members.response.GetMembers;
import com.vk.birthdaynotification.dto.members.response.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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

    public GetMembers getMembers() {
        return vkClient.getMembers(
                groupId,
                accessToken,
                "bdate",
                version
        );
    }

    public List<Item> sortedMembersToBirthDate(GetMembers members) {
        Calendar dateNow = Calendar.getInstance();
        Calendar birthday = Calendar.getInstance();
        dateNow.add(Calendar.DATE, daysBeforeBirthday);
        int dayOfMonth = dateNow.get(Calendar.DAY_OF_MONTH);
        int month = dateNow.get(Calendar.MONTH);
        List<Item> membersForSendingNotification = new ArrayList<>();

        for (Item member : members.getResponse().getItems().stream().filter(i -> i.getBdate() != null).collect(Collectors.toList())) {
            birthday.setTime(member.getBdate());
            if (dayOfMonth == birthday.get(Calendar.DAY_OF_MONTH) && month == birthday.get(Calendar.MONTH)) {
                membersForSendingNotification.add(member);
            }
        }
        return membersForSendingNotification;
    }

    public String sendMessageToUsers(List<Item> users) {
        return vkClient.sendMessages(
                users.stream().map(Item::getID).collect(Collectors.toList()),
                ((int) (Math.random() * 2147483647)),
                accessToken,
                message,
                version
        );
    }
}
