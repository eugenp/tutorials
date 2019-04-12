package org.baeldung.examples.olingo4.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.olingo.commons.api.edm.EdmEntityType;
import org.springframework.stereotype.Component;

@Component
public class RepositoryRegistry {
    
    private Map<String,EdmEntityRepository<?>> repositoriesByClassName = new HashMap<>();

    public RepositoryRegistry(List<EdmEntityRepository<?>> allRepositories) {
        
        allRepositories.stream().forEach((r) ->  
        repositoriesByClassName.put(r.getEdmEntityName(),(EdmEntityRepository<?>)r));
        
    }
    
    
    public EdmEntityRepository<?> getRepositoryForEntity(EdmEntityType entityType) {        
        EdmEntityRepository<?> repo = repositoriesByClassName.get(entityType.getName());
        return repo;
    }
}
