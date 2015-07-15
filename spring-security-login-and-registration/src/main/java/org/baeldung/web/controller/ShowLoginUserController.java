package org.baeldung.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.baeldung.persistence.dao.ActiveUserRepository;
import org.baeldung.persistence.model.ActiveUser;
import org.baeldung.web.util.ActiveUserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class ShowLoginUserController {

    @Autowired
    ActiveUserRepository activeUserRepository;

    public ShowLoginUserController() {
    }

    @RequestMapping(value = "getAllLoggedInUsers")
    public void getAllLoggedInUsers(HttpServletRequest request, HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
        List<ActiveUser> activeUsers = activeUserRepository.findAll();
        // List<ActiveUser> activeUsers = ActiveUserCache.getInstance().findAll();
        List<ActiveUserView> activeUserViews = new ArrayList<ActiveUserView>(activeUsers == null ? 10 : activeUsers.size());
        if (activeUsers != null && !activeUsers.isEmpty()) {
            ActiveUserView userView;
            for (ActiveUser activeUser : activeUsers) {
                userView = new ActiveUserView();
                userView.setActiveTime(activeUser.getActiveTime());
                userView.setEmail(activeUser.getUser().getEmail());
                userView.setIpAddress(activeUser.getIpAddress());
                activeUserViews.add(userView);
            }
        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getWriter(), activeUserViews);
    }
}
