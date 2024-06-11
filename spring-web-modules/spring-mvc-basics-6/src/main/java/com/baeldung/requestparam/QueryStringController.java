package com.baeldung.requestparam;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class QueryStringController {

    @GetMapping("/api/byGetQueryString")
    public String byGetQueryString(HttpServletRequest request) {
        return request.getQueryString();
    }

    @GetMapping("/api/byGetParameter")
    public String byGetParameter(HttpServletRequest request) {
        String username = request.getParameter("username");
        return "username:" + username;
    }

    @GetMapping("/api/byGetParameterValues")
    public String byGetParameterValues(HttpServletRequest request) {
        String[] roles = request.getParameterValues("roles");
        return "roles:" + Arrays.toString(roles);
    }

    @GetMapping("/api/byGetParameterMap")
    public UserDto byGetParameterMap(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        String[] usernames = parameterMap.get("username");
        String[] roles = parameterMap.get("roles");
        UserDto userDto = new UserDto();
        userDto.setUsername(usernames[0]);
        userDto.setRoles(Arrays.asList(roles));
        return userDto;
    }

    @GetMapping("/api/byParameterName")
    public UserDto byParameterName(String username, String[] roles) {
        UserDto userDto = new UserDto();
        userDto.setUsername(username);
        userDto.setRoles(Arrays.asList(roles));
        return userDto;
    }

    @GetMapping("/api/byAnnoRequestParam")
    public UserDto byAnnoRequestParam(@RequestParam("username") String var1, @RequestParam("roles") List<String> var2) {
        UserDto userDto = new UserDto();
        userDto.setUsername(var1);
        userDto.setRoles(var2);
        return userDto;
    }

    @GetMapping("/api/byPojo")
    public UserDto byPojo(UserDto userDto) {
        return userDto;
    }

}
