package com.baeldung.controller;

import com.baeldung.domain.Friend;
import com.baeldung.service.FriendServicePort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController("/friend")
public class FriendController {

    @Resource
    private FriendServicePort friendServicePort;

    @GetMapping("/list")
    public List<Friend> list(Long id) {
        return friendServicePort.list(id);
    }
}
