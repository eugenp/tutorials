package com.midgetontoes.todolist.jpa;

import com.midgetontoes.todolist.model.List;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Collection;

public interface ListRepository extends PagingAndSortingRepository<List, Long> {
    Collection<List> findByUserUsername(String username);
}
