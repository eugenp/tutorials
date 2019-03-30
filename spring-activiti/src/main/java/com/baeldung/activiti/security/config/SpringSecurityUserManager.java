package com.baeldung.activiti.security.config;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.identity.UserQuery;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.UserQueryImpl;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.persistence.entity.GroupEntityImpl;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.activiti.engine.impl.persistence.entity.UserEntityImpl;
import org.activiti.engine.impl.persistence.entity.UserEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.data.UserDataManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

public class SpringSecurityUserManager extends UserEntityManagerImpl {

    private JdbcUserDetailsManager userManager;

    public SpringSecurityUserManager(ProcessEngineConfigurationImpl processEngineConfiguration, UserDataManager userDataManager, JdbcUserDetailsManager userManager) {
        super(processEngineConfiguration, userDataManager);
        this.userManager = userManager;
    }

    @Override
    public UserEntity findById(String userId) {
        UserDetails userDetails = userManager.loadUserByUsername(userId);
        if (userDetails != null) {
            UserEntityImpl user = new UserEntityImpl();
            user.setId(userId);
            return user;
        }
        return null;

    }

    @Override
    public List<User> findUserByQueryCriteria(UserQueryImpl query, Page page) {
        List<User> users = null;
        if (query.getGroupId() != null) {
            users = userManager.findUsersInGroup(query.getGroupId())
                .stream()
                .map(username -> {
                    User user = new UserEntityImpl();
                    user.setId(username);
                    return user;
                })
                .collect(Collectors.toList());
            if (page != null) {
                return users.subList(page.getFirstResult(), page.getFirstResult() + page.getMaxResults());

            }
            return users;
        }

        if (query.getId() != null) {
            UserDetails userDetails = userManager.loadUserByUsername(query.getId());
            if (userDetails != null) {
                UserEntityImpl user = new UserEntityImpl();
                user.setId(query.getId());
                return Collections.singletonList(user);
            }
        }
        return null;
    }

    @Override
    public Boolean checkPassword(String userId, String password) {
        return true;
    }

    public void setUserManager(JdbcUserDetailsManager userManager) {
        this.userManager = userManager;
    }

    public User createNewUser(String userId) {
        throw new UnsupportedOperationException("This operation is not supported!");
    }

    public void updateUser(User updatedUser) {
        throw new UnsupportedOperationException("This operation is not supported!");

    }

    public void delete(UserEntity userEntity) {
        throw new UnsupportedOperationException("This operation is not supported!");

    }

    @Override
    public void deletePicture(User user) {
        UserEntity userEntity = (UserEntity) user;
        if (userEntity.getPictureByteArrayRef() != null) {
            userEntity.getPictureByteArrayRef()
                .delete();
        }
    }

    public void delete(String userId) {
        throw new UnsupportedOperationException("This operation is not supported!");

    }

    public long findUserCountByQueryCriteria(UserQueryImpl query) {
        return findUserByQueryCriteria(query, null).size();
    }

    public List<Group> findGroupsByUser(String userId) {
        UserDetails userDetails = userManager.loadUserByUsername(userId);
        if (userDetails != null) {
            List<Group> groups = userDetails.getAuthorities()
                .stream()
                .map(a -> a.getAuthority())
                .map(a -> {
                    Group g = new GroupEntityImpl();
                    g.setId(a);
                    return g;
                })
                .collect(Collectors.toList());
            return groups;
        }
        return null;
    }

    public UserQuery createNewUserQuery() {
        throw new UnsupportedOperationException("This operation is not supported!");
    }

    public List<User> findUsersByNativeQuery(Map<String, Object> parameterMap, int firstResult, int maxResults) {
        throw new UnsupportedOperationException("This operation is not supported!");
    }

    public long findUserCountByNativeQuery(Map<String, Object> parameterMap) {
        throw new UnsupportedOperationException("This operation is not supported!");

    }

}
