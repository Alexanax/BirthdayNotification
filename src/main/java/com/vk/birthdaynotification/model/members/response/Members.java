package com.vk.birthdaynotification.model.members.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class Members {
    @Getter(onMethod_ = {@JsonProperty("response")})
    @Setter(onMethod_ = {@JsonProperty("response")})
    private Response response;
}
