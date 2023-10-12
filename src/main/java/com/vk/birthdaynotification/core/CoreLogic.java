package com.vk.birthdaynotification.core;

import com.vk.birthdaynotification.model.Converter;
import com.vk.birthdaynotification.model.members.response.Item;
import com.vk.birthdaynotification.model.members.response.Members;
import com.vk.birthdaynotification.model.messages.conversations.response.Profile;
import com.vk.birthdaynotification.model.messages.conversations.response.ResponseConversation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CoreLogic {

    @Value("${notification.daysBefore}")
    private Integer daysBeforeBirthday;

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

    public List<Profile> sortedConversationToBirthDate(ResponseConversation responseConversation) {
        List<Profile> membersForSendingNotification = new ArrayList<>();

        for (Profile profile : responseConversation.getResponse().getProfiles().stream()
                .filter(i -> i.getBdate() != null)
                .collect(Collectors.toList())) {
            OffsetDateTime offsetDateTime = Converter.parseDateTimeString(profile.getBdate());
            if (LocalDate.now().plusDays(daysBeforeBirthday).getMonthValue() == (offsetDateTime.toLocalDate().getMonthValue())
            && LocalDate.now().plusDays(daysBeforeBirthday).getDayOfMonth() == (offsetDateTime.toLocalDate().getDayOfMonth()))
            {
                membersForSendingNotification.add(profile);
            }
        }
        System.out.println("Отобраны пользователи для отправки уведомления : " + membersForSendingNotification);
        return membersForSendingNotification;
    }
}
