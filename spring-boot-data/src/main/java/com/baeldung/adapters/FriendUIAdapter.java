package com.baeldung.adapters;

import com.baeldung.domain.Friend;
import com.baeldung.ports.FriendUIPort;
import com.baeldung.service.FriendService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class FriendUIAdapter implements FriendUIPort {
    @Resource
    private FriendService friendService;

    @Override
    public Map<String, List<Friend>> getFriends(Long id) {
        return friendService.friends(id);
    }
}
