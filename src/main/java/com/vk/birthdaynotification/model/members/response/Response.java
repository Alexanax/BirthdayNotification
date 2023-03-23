package com.vk.birthdaynotification.model.members.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
public class Response {
    @Getter(onMethod_ = {@JsonProperty("count")})
    @Setter(onMethod_ = {@JsonProperty("count")})
    private Long count;
    @Getter(onMethod_ = {@JsonProperty("items")})
    @Setter(onMethod_ = {@JsonProperty("items")})
    private List<Item> items;
    @Getter(onMethod_ = {@JsonProperty("next_from")})
    @Setter(onMethod_ = {@JsonProperty("next_from")})
    private String nextFrom;
}

