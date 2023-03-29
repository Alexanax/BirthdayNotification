package com.vk.birthdaynotification.client;

import com.vk.birthdaynotification.config.FeignConfig;
import com.vk.birthdaynotification.model.members.response.Members;
import com.vk.birthdaynotification.model.sendmessage.response.SendMessage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(
        value = "vk",
        url = "https://api.vk.com/method/",
        configuration = FeignConfig.class
)
public interface VKClient {

    @RequestMapping(method = RequestMethod.POST, value = "groups.getMembers")
    Members getMembers(
            @RequestParam("group_id") String groupId,
            @RequestParam("access_token") String accessToken,
            @RequestParam("fields") String fields,
            @RequestParam("v") String version);

    @RequestMapping(method = RequestMethod.POST, value = "messages.send")
    SendMessage sendMessages(
            @RequestParam("user_ids") List<Long> userIds,
            @RequestParam("random_id") Integer randomId,
            @RequestParam("access_token") String accessToken,
            @RequestParam("message") String message,
            @RequestParam("v") String version);
}
