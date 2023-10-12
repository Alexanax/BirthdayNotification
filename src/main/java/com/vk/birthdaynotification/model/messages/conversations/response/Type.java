package com.vk.birthdaynotification.model.messages.conversations.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.IOException;


public enum Type {
    @JsonProperty("user")
    USER;

    public String toValue() {
        if (this == Type.USER) {
            return "user";
        }
        return null;
    }

    public static Type forValue(String value) throws IOException {
        if (value.equals("user")) return USER;
        throw new IOException("Cannot deserialize Type");
    }
}
