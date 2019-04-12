/**
 * 
 */
package org.baeldung.examples.olingo4.repository;


/**
 * @author Philippe
 *
 */
public interface EdmEntityRepository<E>  {
    
    public String getEdmEntityName();
    public Class<E> getEntityClass();


}
