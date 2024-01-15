package com.baeldung.listvsset.eager.moderateset;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupService {

    private final GroupRepository groupRepository;

    @Autowired
    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public Optional<Group> findById(Long aLong) {
        return groupRepository.findById(aLong);
    }

    public List<Group> findAll() {
        return groupRepository.findAll();
    }
}
