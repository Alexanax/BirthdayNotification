package com.vk.birthdaynotification.model.messages.conversations.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Profile {
    private long id;
    private long sex;
    private String screenName;
    private String photo50;
    private String photo100;
    private OnlineInfo onlineInfo;
    private long online;
    private String firstName;
    private String lastName;
    private boolean canAccessClosed;
    private boolean isClosed;
    private String bdate;
}
