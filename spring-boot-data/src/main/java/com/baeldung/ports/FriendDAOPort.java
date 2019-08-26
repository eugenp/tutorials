package com.baeldung.ports;

import com.baeldung.domain.Friend;

import java.util.List;

public interface FriendDAOPort {
    List<Friend> list(Long id);
}
