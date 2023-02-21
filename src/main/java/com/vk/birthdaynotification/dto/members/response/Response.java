package com.vk.birthdaynotification.dto.members.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Response {
    private long count;
    private List<Item> items;
    private String nextFrom;

    @JsonProperty("count")
    public long getCount() {
        return count;
    }

    @JsonProperty("count")
    public void setCount(long value) {
        this.count = value;
    }

    @JsonProperty("items")
    public List<Item> getItems() {
        return items;
    }

    @JsonProperty("items")
    public void setItems(List<Item> value) {
        this.items = value;
    }

    @JsonProperty("next_from")
    public String getNextFrom() {
        return nextFrom;
    }

    @JsonProperty("next_from")
    public void setNextFrom(String value) {
        this.nextFrom = value;
    }
}
