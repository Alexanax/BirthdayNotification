package com.vk.birthdaynotification.model.messages.sendmessage.response;

import lombok.Data;

@Data
public class Response {
    private Long peerID;
    private Long messageID;
    private Error error;
}
