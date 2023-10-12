package com.vk.birthdaynotification.model.messages.sendmessage.response;

import lombok.Data;

@Data
public class Error {
    private Long code;
    private String description;
}
