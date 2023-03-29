package com.vk.birthdaynotification.controller;

import com.vk.birthdaynotification.model.members.response.Members;
import com.vk.birthdaynotification.model.members.response.Item;
import com.vk.birthdaynotification.services.VKService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Objects;

@Controller
public class BirthdayNotificationController {
    private final VKService vkService;

    @Autowired
    public BirthdayNotificationController(VKService vkService) {
        this.vkService = vkService;
    }

    @Scheduled(cron = "* 0 18 * * 0-6")
    public void sendNotificationBeforeBirthday(){
        Members members = vkService.getMembers();
        List<Item> memberForNotification = vkService.sortedMembersToBirthDate(Objects.requireNonNull(members));
        vkService.sendMessageToUsers(memberForNotification);
        System.out.println("Сообщение отправлено пользавателю");
    }
}
