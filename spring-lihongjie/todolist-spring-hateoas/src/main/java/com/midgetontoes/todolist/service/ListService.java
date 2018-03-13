package com.midgetontoes.todolist.service;

import com.midgetontoes.todolist.jpa.ListRepository;
import com.midgetontoes.todolist.model.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class ListService {
    @Autowired
    private ListRepository listRepository;

    public List findOne(Long id) {
        return listRepository.findOne(id);
    }

    public Collection<List> findByUserUsername(String username) {
        return listRepository.findByUserUsername(username);
    }

    public List save(List list) {
        return listRepository.save(list);
    }

    public void delete(Long id) {
        listRepository.delete(id);
    }
}
