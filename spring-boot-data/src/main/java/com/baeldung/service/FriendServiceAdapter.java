package com.baeldung.service;

import com.baeldung.domain.Friend;
import com.baeldung.repository.FriendRepositoryPort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FriendServiceAdapter implements FriendServicePort {

    @Resource
    private FriendRepositoryPort repositoryPort;

    @Override
    public List<Friend> list(Long id) {
        return repositoryPort.list(id);
    }
}
