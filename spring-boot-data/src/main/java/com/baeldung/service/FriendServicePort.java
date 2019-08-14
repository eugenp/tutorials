package com.baeldung.service;

import com.baeldung.domain.Friend;

import java.util.List;

public interface FriendServicePort {
    List<Friend> list(Long id);
}
