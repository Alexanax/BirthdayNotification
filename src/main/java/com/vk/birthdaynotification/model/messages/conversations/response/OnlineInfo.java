package com.vk.birthdaynotification.model.messages.conversations.response;

@lombok.Data
public class OnlineInfo {
    private boolean visible;
    private Status status;
    private Long lastSeen;
    private Boolean isOnline;
    private Long appID;
    private Boolean isMobile;
}
