/**
 * This is the boundary - a REST API that uses the domain. It has the responsibility to
 * <ul>
 *     <li>define and implement the REST API with operations and data types (DTOs) - {@link com.baeldung.pattern.hexagonal.boundary.controllers.EmployeeController}</li>
 *     <li>map the operations and DTOs to the domain and vice-versa - {@link com.baeldung.pattern.hexagonal.boundary.mappers.EmployeeDtoMapper}</li>
 *     <li>map the exceptions from the domain to HTTP responses - {@link com.baeldung.pattern.hexagonal.boundary.GlobalExceptionHandler}</li>
 * </ul>
 */
package com.baeldung.pattern.hexagonal.boundary;
