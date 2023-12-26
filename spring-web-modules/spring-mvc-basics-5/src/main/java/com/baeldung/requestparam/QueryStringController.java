package com.baeldung.requestparam;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
public class QueryStringController {

    @GetMapping("/api/user0")
    public String byGetQueryString(HttpServletRequest request) {
        return request.getQueryString();
    }

    @GetMapping("/api/user1")
    public String byGetParameter(HttpServletRequest request) {
        String username = request.getParameter("username");
        return "username:" + username;
    }

    @GetMapping("/api/user2")
    public String byGetParameterValues(HttpServletRequest request) {
        String[] roles = request.getParameterValues("roles");
        return "roles:" + Arrays.toString(roles);
    }

    @GetMapping("/api/user3")
    public UserDTO byGetParameterMap(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        String[] usernames = parameterMap.get("username");
        String[] roles = parameterMap.get("roles");
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(usernames[0]);
        userDTO.setRoles(Arrays.asList(roles));
        return userDTO;
    }

    @GetMapping("/api/user4")
    public UserDTO byParameterName(String username, String[] roles) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(username);
        userDTO.setRoles(Arrays.asList(roles));
        return userDTO;
    }

    @GetMapping("/api/user5")
    public UserDTO byRequestParamAnnotation(@RequestParam("username") String var1, @RequestParam("roles") List<String> var2) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(var1);
        userDTO.setRoles(var2);
        return userDTO;
    }

    @GetMapping("/api/user6")
    public UserDTO byPojo(UserDTO userDTO) {
        return userDTO;
    }

}
