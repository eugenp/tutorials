/**
 * 
 */
package org.baeldung.examples.olingo4.processor;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.persistence.EntityManagerFactory;
import javax.persistence.metamodel.EntityType;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.olingo.commons.api.data.Entity;
import org.apache.olingo.commons.api.data.Property;
import org.apache.olingo.commons.api.data.ValueType;
import org.apache.olingo.commons.api.edm.EdmEntitySet;
import org.apache.olingo.commons.api.ex.ODataRuntimeException;
import org.springframework.stereotype.Component;

/**
 * <p>Helper class that converts a JPA entity into an OData entity using
 * available metadata from the JPA's EntityManagerFactory.</p>
 * 
 * @author Philippe
 *
 */
@Component
public class JpaEntityMapper {
    
    private EntityManagerFactory emf;

    public JpaEntityMapper(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    
    public Entity map2entity(EdmEntitySet edmEntitySet, Object entry) {

        EntityType<?> et = emf.getMetamodel()
            .entity(entry.getClass());


        Entity e = new Entity();
        try {
            et.getDeclaredSingularAttributes().stream()
              .forEach( (attr) -> {
                 if ( !attr.isAssociation()) {
                     Object v = getPropertyValue(entry,attr.getName());
                     Property p = new Property(null, attr.getName(),ValueType.PRIMITIVE,v);
                     e.addProperty(p);

                     if ( attr.isId()) {
                         e.setId(createId(edmEntitySet.getName(),v));                     
                     }                     
                 }                 
              });
        } catch (Exception ex) {
            throw new ODataRuntimeException("[E141] Unable to create OData entity", ex);
        }

        return e;
    }
    
    
    private Object getPropertyValue(Object entry, String name) {
        try {
            return PropertyUtils.getProperty(entry,name);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new ODataRuntimeException("[E141] Unable to read property from entity, property=" + name, e);
        }
    }

    private URI createId(String entitySetName, Object id)  {
        try {
            return new URI(entitySetName + "(" + String.valueOf(id) + ")");
        } catch (URISyntaxException e) {
            throw new ODataRuntimeException("[E177] Unable to create URI", e);
        }
    }
    


}
