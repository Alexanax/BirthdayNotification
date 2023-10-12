package com.vk.birthdaynotification.model.messages.conversations.response;

@lombok.Data
public class Doc {
    private long id;
    private long ownerID;
    private String title;
    private long size;
    private String ext;
    private long date;
    private long type;
    private String url;
    private long isUnsafe;
    private String accessKey;
}
