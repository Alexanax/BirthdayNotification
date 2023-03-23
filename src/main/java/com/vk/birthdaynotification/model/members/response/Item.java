package com.vk.birthdaynotification.model.members.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    @Getter(onMethod_ = {@JsonProperty("id")})
    @Setter(onMethod_ = {@JsonProperty("id")})
    private Long id;
    @Getter(onMethod_ = {@JsonProperty("first_name")})
    @Setter(onMethod_ = {@JsonProperty("first_name")})
    private String firstName;
    @Getter(onMethod_ = {@JsonProperty("last_name")})
    @Setter(onMethod_ = {@JsonProperty("last_name")})
    private String lastName;
    @Getter(onMethod_ = {@JsonProperty("can_access_closed")})
    @Setter(onMethod_ = {@JsonProperty("can_access_closed")})
    private Boolean canAccessClosed;
    @Getter(onMethod_ = {@JsonProperty("is_closed")})
    @Setter(onMethod_ = {@JsonProperty("is_closed")})
    private Boolean isClosed;
    @Getter(onMethod_ = {@JsonProperty("bdate")})
    @Setter(onMethod_ = {@JsonProperty("bdate")})
    private String bdate;
}
