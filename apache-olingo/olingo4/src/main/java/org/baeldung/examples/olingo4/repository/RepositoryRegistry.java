package org.baeldung.examples.olingo4.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.olingo.commons.api.edm.EdmEntityType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public class RepositoryRegistry {
    
    private Map<String,JpaRepository<?,?>> repositoriesByClassName = new HashMap<>();

    public RepositoryRegistry(List<EdmEntityRepository> allRepositories) {
        
        allRepositories.stream().forEach((r) ->  
        repositoriesByClassName.put(r.getEdmEntityName(),(JpaRepository<?,?>)r));
        
    }
    
    
    public JpaRepository<?, ?> getRepositoryForEntity(EdmEntityType entityType) {        
        JpaRepository<?, ?> repo = repositoriesByClassName.get(entityType.getName());
        return repo;
    }
}
