package com.vk.birthdaynotification.services;

import com.vk.birthdaynotification.client.VKClient;

import com.vk.birthdaynotification.model.members.response.Members;
import com.vk.birthdaynotification.model.members.response.Item;
import com.vk.birthdaynotification.model.messages.conversations.response.Profile;
import com.vk.birthdaynotification.model.messages.conversations.response.ResponseConversation;
import com.vk.birthdaynotification.model.messages.sendmessage.response.SendMessage;
import com.vk.birthdaynotification.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class VKService {
    private VKClient vkClient;
    private Utils utils;

    @Value("${vk.group.id}")
    private String groupId;

    @Value("${vk.api.accessToken}")
    private String accessToken;

    @Value("${vk.api.version}")
    private String version;

    @Value("${notification.message}")
    private String message;

    @Autowired
    public VKService(VKClient vkClient, Utils utils) {
        this.vkClient = vkClient;
        this.utils = utils;
    }

    public Members getMembers() {
        return vkClient.getMembers(
                groupId,
                accessToken,
                "bdate",
                version
        );
    }

    public SendMessage sendMessageToUsersMembers(List<Item> usersForSendingMessage) {
        var messageResponse = vkClient.sendMessages(
                Utils.getIdsUsersFromItem(usersForSendingMessage),
                Utils.getRandomId(),
                accessToken,
                message,
                version
        );
        if (Utils.messageHasError(messageResponse)) {
            alertAdmin(usersForSendingMessage);
        } else {
            System.out.println("В попытке отправить сообщения пользователям вернулось ответное сообщение " + messageResponse);
        }
        return messageResponse;
    }

    public SendMessage sendMessageToUsersFromConversations(List<Profile> usersForSendingMessage) {
        var messageResponse = vkClient.sendMessages(
                Utils.getIdsUsersFromProfile(usersForSendingMessage),
                Utils.getRandomId(),
                accessToken,
                message,
                version
        );
        if (Utils.messageHasError(messageResponse)) {
            System.out.println("В попытке отправить сообщения пользователям вернулось ответное сообщение " + messageResponse);
        }
        return messageResponse;
    }

    public ResponseConversation getConversations() {
        return vkClient.getConversations(
                null,
                null,
                null,
                1,
                null,
                "bdate",
                null,
                accessToken,
                version
        );
    }

    private void alertAdmin(List<Item> usersWhoHaveNotReceivedMessage) {
        vkClient.sendMessages(
                List.of(60528638L),
                Utils.getRandomId(),
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
}
