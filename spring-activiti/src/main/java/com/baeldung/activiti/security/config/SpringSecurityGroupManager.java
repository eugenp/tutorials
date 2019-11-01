package com.baeldung.activiti.security.config;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.GroupQuery;
import org.activiti.engine.impl.GroupQueryImpl;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.persistence.entity.GroupEntityImpl;
import org.activiti.engine.impl.persistence.entity.GroupEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.data.GroupDataManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

public class SpringSecurityGroupManager extends GroupEntityManagerImpl {

    private JdbcUserDetailsManager userManager;

    public SpringSecurityGroupManager(ProcessEngineConfigurationImpl processEngineConfiguration, GroupDataManager groupDataManager) {
        super(processEngineConfiguration, groupDataManager);
    }

    @Override
    public List<Group> findGroupByQueryCriteria(GroupQueryImpl query, Page page) {

        if (query.getUserId() != null) {
            return findGroupsByUser(query.getUserId());
        }
        return null;
    }

    @Override
    public long findGroupCountByQueryCriteria(GroupQueryImpl query) {
        return findGroupByQueryCriteria(query, null).size();
    }

    @Override
    public List<Group> findGroupsByUser(String userId) {
        UserDetails userDetails = userManager.loadUserByUsername(userId);
        System.out.println("group manager");
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

    public void setUserManager(JdbcUserDetailsManager userManager) {
        this.userManager = userManager;
    }

    public Group createNewGroup(String groupId) {
        throw new UnsupportedOperationException("This operation is not supported!");

    }

    @Override
    public void delete(String groupId) {
        throw new UnsupportedOperationException("This operation is not supported!");

    }

    public GroupQuery createNewGroupQuery() {
        throw new UnsupportedOperationException("This operation is not supported!");
    }

    public List<Group> findGroupsByNativeQuery(Map<String, Object> parameterMap, int firstResult, int maxResults) {
        throw new UnsupportedOperationException("This operation is not supported!");
    }

    public long findGroupCountByNativeQuery(Map<String, Object> parameterMap) {
        throw new UnsupportedOperationException("This operation is not supported!");
    }

}
