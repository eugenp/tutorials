package com.baeldung;

import com.baeldung.ports.FriendUIPort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/friend")
public class FriendController {
    @Resource
    private FriendUIPort friendUIPort;

    @GetMapping("/list")
    public Object friends() {
        return friendUIPort.getFriends(1L);
    }
}
