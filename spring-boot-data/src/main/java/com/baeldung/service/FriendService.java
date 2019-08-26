package com.baeldung.service;

import com.baeldung.domain.Friend;
import com.baeldung.ports.FriendDAOPort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

import static java.util.Comparator.comparingLong;
import static java.util.stream.Collectors.groupingBy;

@Service
public class FriendService {
    @Resource
    private FriendDAOPort friendDAOPort;

    public Map<String, List<Friend>> friends(Long id) {
        List<Friend> friends = friendDAOPort.list(id);
        return friends.stream()
                .sorted(comparingLong(Friend::getUpdateTime).reversed())
                .collect(groupingBy(Friend::getGroup));
    }
}
