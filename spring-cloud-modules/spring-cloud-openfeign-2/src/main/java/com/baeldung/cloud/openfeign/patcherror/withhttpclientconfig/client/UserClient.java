package com.baeldung.cloud.openfeign.patcherror.withhttpclientconfig.client;

import com.baeldung.cloud.openfeign.patcherror.withhttpclientconfig.config.FeignConfig;
import com.baeldung.cloud.openfeign.patcherror.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "user-client-1", url = "${user.api.url}", configuration = FeignConfig.class)
public interface UserClient {

    @RequestMapping(value = "{userId}", method = RequestMethod.GET)
    User getUser(@PathVariable(value = "userId") String userId);

    @RequestMapping(value = "{userId}", method = RequestMethod.PATCH)
    User updateUser(@PathVariable(value = "userId") String userId, @RequestBody User user);

}