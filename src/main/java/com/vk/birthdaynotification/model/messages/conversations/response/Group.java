package com.vk.birthdaynotification.model.messages.conversations.response;

@lombok.Data
public class Group {
        private long id;
        private String name;
        private String screenName;
        private long isClosed;
        private String type;
        private String photo50;
        private String photo100;
        private String photo200;
}
