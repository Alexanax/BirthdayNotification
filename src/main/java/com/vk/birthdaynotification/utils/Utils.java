package com.vk.birthdaynotification.utils;

import com.vk.birthdaynotification.model.members.response.Item;
import com.vk.birthdaynotification.model.messages.conversations.response.Profile;
import com.vk.birthdaynotification.model.messages.sendmessage.response.SendMessage;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class Utils {
    public static List<Long> getIdsUsersFromItem(List<Item> usersForSendingMessage) {
        return usersForSendingMessage.stream().map(Item::getId).collect(Collectors.toList());
    }
    public static List<Long> getIdsUsersFromProfile(List<Profile> usersForSendingMessage) {
        return usersForSendingMessage.stream().map(Profile::getId).collect(Collectors.toList());
    }

    public static Integer getRandomId() {
        return ((int) (Math.random() * 2147483647));
    }

    public static boolean messageHasError(SendMessage message) {
        if (message.getResponse() != null) {
            return message.getResponse().stream().anyMatch(r -> r.getError() != null);
        }
        else return false;
    }
}
