package com.vk.birthdaynotification.model.messages.conversations.response;

@lombok.Data
public class Conversation {
    private Peer peer;
    private long lastMessageID;
    private long inRead;
    private long outRead;
    private SortID sortID;
    private long lastConversationMessageID;
    private long inReadCmid;
    private long outReadCmid;
    private boolean isMarkedUnread;
    private boolean important;
    private CanWrite canWrite;
    private Boolean unanswered;
}
