package com.baeldung.listvsset.eager.list.moderatedomain;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
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

    public <S extends Group> S save(S entity) {
        return groupRepository.save(entity);
    }
}
