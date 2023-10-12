package com.vk.birthdaynotification.model.messages.conversations.response;

import java.util.List;

@lombok.Data
public class Photo {
    private long albumID;
    private long date;
    private long id;
    private long ownerID;
    private List<Size> sizes;
    private String text;
    private long userID;
    private boolean hasTags;
}
