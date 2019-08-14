package com.baeldung.repository;

import com.baeldung.domain.Friend;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, Long> {
    List<Friend> list(Long id);
}
