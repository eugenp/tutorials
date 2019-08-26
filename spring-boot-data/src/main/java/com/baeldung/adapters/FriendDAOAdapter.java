package com.baeldung.adapters;

import com.baeldung.domain.Friend;
import com.baeldung.ports.FriendDAOPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendDAOAdapter implements FriendDAOPort {
    @Override
    public List<Friend> list(Long id) {
        // Call external component to query friends data and convert the entities to friends domain
        return null;
    }
}
