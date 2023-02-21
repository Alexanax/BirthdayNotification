package com.vk.birthdaynotification.dto.members.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetMembers {
    private Response response;

    @JsonProperty("response")
    public Response getResponse() {
        return response;
    }
}
