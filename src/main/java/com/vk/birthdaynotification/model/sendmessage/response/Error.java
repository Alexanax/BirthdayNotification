package com.vk.birthdaynotification.model.sendmessage.response;

import lombok.Data;

@Data
public class Error {
    private Long code;
    private String description;
}
