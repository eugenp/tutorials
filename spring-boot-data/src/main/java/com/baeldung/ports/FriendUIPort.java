package com.baeldung.ports;

import com.baeldung.domain.Friend;

import java.util.List;
import java.util.Map;

public interface FriendUIPort {
    Map<String, List<Friend>> getFriends(Long id);
}
