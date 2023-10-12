package com.vk.birthdaynotification.model.messages.conversations.response;

@lombok.Data
public class Link {
    private String url;
    private String title;
    private String caption;
    private String description;
    private Photo photo;
    private boolean isFavorite;
}
