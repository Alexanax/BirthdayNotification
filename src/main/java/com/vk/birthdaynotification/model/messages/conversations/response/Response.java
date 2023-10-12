package com.vk.birthdaynotification.model.messages.conversations.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Response {
    private long count;
    private List<Item> items;
    private List<Profile> profiles;
    private List<Group> groups;

    public void setCount(long count) {
        this.count = count;
    }
}
