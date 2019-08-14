package com.baeldung.repository;

import com.baeldung.domain.Friend;

import java.util.List;

public interface FriendRepositoryPort {
    List<Friend> list(Long id);
}
