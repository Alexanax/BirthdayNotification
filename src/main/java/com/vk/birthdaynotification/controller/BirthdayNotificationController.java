package com.vk.birthdaynotification.controller;

import com.vk.birthdaynotification.dto.members.response.GetMembers;
import com.vk.birthdaynotification.dto.members.response.Item;
import com.vk.birthdaynotification.services.VKService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class BirthdayNotificationController {
    private final VKService vkService;

    @Autowired
    public BirthdayNotificationController(VKService vkService) {
        this.vkService = vkService;
    }

    @Scheduled(cron = "0 12 * * 1-7")
    public void sendNotificationBeforeBirthday() {
        GetMembers members = vkService.getMembers();
        List<Item> memberForNotification = vkService.sortedMembersToBirthDate(members);
        vkService.sendMessageToUsers(memberForNotification);
    }
}
