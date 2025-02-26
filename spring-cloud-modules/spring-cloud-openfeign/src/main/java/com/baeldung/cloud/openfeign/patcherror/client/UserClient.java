package com.baeldung.cloud.openfeign.patcherror.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.baeldung.cloud.openfeign.patcherror.model.User;

@FeignClient(name = "user-client", url = "${user.api.url}")
public interface UserClient {

    @RequestMapping(value = "{userId}", method = RequestMethod.PATCH)
    User updateUser(@PathVariable(value = "userId") String userId, @RequestBody User user);

}
