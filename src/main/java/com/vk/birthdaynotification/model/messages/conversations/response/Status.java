package com.vk.birthdaynotification.model.messages.conversations.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.IOException;

public enum Status {
    @JsonProperty("recently")
    RECENTLY;

    public String toValue() {
        if (this == Status.RECENTLY) {
            return "recently";
        }
        return null;
    }

    public static Status forValue(String value) throws IOException {
        if (value.equals("recently")) return RECENTLY;
        throw new IOException("Cannot deserialize Status");
    }
}
