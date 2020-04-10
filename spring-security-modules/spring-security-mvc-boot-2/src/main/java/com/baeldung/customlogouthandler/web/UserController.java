package com.baeldung.customlogouthandler.web;

import com.baeldung.customlogouthandler.services.UserCache;
import com.baeldung.customlogouthandler.user.User;
import com.baeldung.customlogouthandler.user.UserUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(path = "/user")
public class UserController {

    private final UserCache userCache;

    public UserController(UserCache userCache) {
        this.userCache = userCache;
    }

    @GetMapping(path = "/language")
    @ResponseBody
    public String getLanguage() {
        String login = UserUtils.getAuthenticatedUserLogin();
        User user = userCache.getByLogin(login);
        return user.getLanguage();
    }

}
