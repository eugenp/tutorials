package com.baeldung.repository;

import com.baeldung.domain.Friend;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FriendRepositoryAdapter implements FriendRepositoryPort {

    @Resource
    private FriendRepository friendRepository;

    @Override
    public List<Friend> list(Long id) {
        return friendRepository.list(id);
    }
}
