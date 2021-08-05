/**
 * This is the persistence layer - a JPA Crud Repository. It has the responsibility to
 * <ul>
 *     <li>implement the database access operations and data types (entities) - {@link com.baeldung.pattern.hexagonal.persistence.entities.EmployeeEntity}</li>
 *     <li>map the operations and entities to the domain and vice-versa - {@link com.baeldung.pattern.hexagonal.persistence.mappers.EmployeeEntityMapper}</li>
 *     <li>map the exceptions from the database access layer to the domain</li>
 * </ul>
 */
package com.baeldung.pattern.hexagonal.persistence;
