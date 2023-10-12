package com.vk.birthdaynotification.model.messages.sendmessage.response;


import lombok.Data;

import java.util.List;

@Data
public class SendMessage {
    private List<Response> response;
}
