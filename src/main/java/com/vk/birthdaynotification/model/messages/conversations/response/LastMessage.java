package com.vk.birthdaynotification.model.messages.conversations.response;

import java.util.List;

@lombok.Data
public class LastMessage {
    private long date;
    private long fromID;
    private long id;
    private long out;
    private List<Attachment> attachments;
    private long conversationMessageID;
    private List<Object> fwdMessages;
    private boolean important;
    private boolean isHidden;
    private long peerID;
    private long randomID;
    private String text;
    private Long adminAuthorID;
}
