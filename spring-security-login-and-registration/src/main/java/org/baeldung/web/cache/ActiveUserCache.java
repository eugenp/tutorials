package org.baeldung.web.cache;


import java.util.LinkedList;
import java.util.List;

import org.baeldung.persistence.dao.ActiveUserRepository;
import org.baeldung.persistence.model.ActiveUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class ActiveUserCache implements ActiveUserRepository {
    List<ActiveUser> users = new LinkedList<ActiveUser>();
    
    private static final ActiveUserCache INSTANCE = new ActiveUserCache();
    
    public ActiveUserCache() {

    }

    public void delete(ActiveUser user) {
        users.remove(user);
    }

    public ActiveUser save(ActiveUser user) {
        users.add(user);
        return user;
    }
    
    public static ActiveUserCache getInstance() {
        return INSTANCE;
    }

    public List<ActiveUser> findAll() {
        return new LinkedList<ActiveUser>(users);
    }

    public List<ActiveUser> findAll(Sort sort) {
        return null;
    }

    public List<ActiveUser> findAll(Iterable<Long> ids) {
        return null;
    }

    public <S extends ActiveUser> List<S> save(Iterable<S> entities) {
        return null;
    }

    public void flush() {
        
    }

    public <S extends ActiveUser> S saveAndFlush(S entity) {
        return null;
    }

    public void deleteInBatch(Iterable<ActiveUser> entities) {
        
    }

    public void deleteAllInBatch() {
        
    }

    public ActiveUser getOne(Long id) {
        return null;
    }

    public Page<ActiveUser> findAll(Pageable pageable) {
        return null;
    }

    public ActiveUser findOne(Long id) {
        return null;
    }

    public boolean exists(Long id) {
        return false;
    }

    public long count() {
        return 0;
    }

    public void delete(Long id) {
        
    }

    public void delete(Iterable<? extends ActiveUser> entities) {
        
    }

    public void deleteAll() {
        
    }
}
