package com.vk.birthdaynotification.controller;

import com.vk.birthdaynotification.core.CoreLogic;
import com.vk.birthdaynotification.model.members.response.Members;
import com.vk.birthdaynotification.model.members.response.Item;
import com.vk.birthdaynotification.model.messages.conversations.response.Profile;
import com.vk.birthdaynotification.model.messages.conversations.response.ResponseConversation;
import com.vk.birthdaynotification.services.VKService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Objects;

@Controller
public class BirthdayNotificationController {
    private final VKService vkService;
    private final CoreLogic coreLogic;

    @Autowired
    public BirthdayNotificationController(VKService vkService, CoreLogic coreLogic) {
        this.vkService = vkService;
        this.coreLogic = coreLogic;
    }

    @Scheduled(cron = "0 0 18 * * 0-6")
    public void sendNotificationBeforeBirthday() {
        Members members = vkService.getMembers();
        List<Item> memberForNotification = coreLogic.sortedMembersToBirthDate(Objects.requireNonNull(members));
        vkService.sendMessageToUsersMembers(memberForNotification);
    }

    @Scheduled(cron = "0 0 18 * * 0-6")
    public void sendNotificationBeforeBirthdayToParticipantsFromConversations() {
        ResponseConversation conversations = vkService.getConversations();
        List<Profile> memberForNotification = coreLogic.sortedConversationToBirthDate(conversations);
        vkService.sendMessageToUsersFromConversations(memberForNotification);
    }
}
