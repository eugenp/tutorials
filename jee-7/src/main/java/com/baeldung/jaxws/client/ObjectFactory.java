
package com.baeldung.jaxws.client;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.baeldung.jaxws.client package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _AddEmployeeResponse_QNAME = new QName("http://bottomup.server.jaxws.baeldung.com/", "addEmployeeResponse");
    private final static QName _EmployeeAlreadyExists_QNAME = new QName("http://bottomup.server.jaxws.baeldung.com/", "EmployeeAlreadyExists");
    private final static QName _GetEmployeeResponse_QNAME = new QName("http://bottomup.server.jaxws.baeldung.com/", "getEmployeeResponse");
    private final static QName _EmployeeNotFound_QNAME = new QName("http://bottomup.server.jaxws.baeldung.com/", "EmployeeNotFound");
    private final static QName _CountEmployees_QNAME = new QName("http://bottomup.server.jaxws.baeldung.com/", "countEmployees");
    private final static QName _UpdateEmployee_QNAME = new QName("http://bottomup.server.jaxws.baeldung.com/", "updateEmployee");
    private final static QName _DeleteEmployeeResponse_QNAME = new QName("http://bottomup.server.jaxws.baeldung.com/", "deleteEmployeeResponse");
    private final static QName _GetAllEmployeesResponse_QNAME = new QName("http://bottomup.server.jaxws.baeldung.com/", "getAllEmployeesResponse");
    private final static QName _DeleteEmployee_QNAME = new QName("http://bottomup.server.jaxws.baeldung.com/", "deleteEmployee");
    private final static QName _UpdateEmployeeResponse_QNAME = new QName("http://bottomup.server.jaxws.baeldung.com/", "updateEmployeeResponse");
    private final static QName _AddEmployee_QNAME = new QName("http://bottomup.server.jaxws.baeldung.com/", "addEmployee");
    private final static QName _GetAllEmployees_QNAME = new QName("http://bottomup.server.jaxws.baeldung.com/", "getAllEmployees");
    private final static QName _CountEmployeesResponse_QNAME = new QName("http://bottomup.server.jaxws.baeldung.com/", "countEmployeesResponse");
    private final static QName _GetEmployee_QNAME = new QName("http://bottomup.server.jaxws.baeldung.com/", "getEmployee");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.baeldung.jaxws.client
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link EmployeeNotFound }
     * 
     */
    public EmployeeNotFound createEmployeeNotFound() {
        return new EmployeeNotFound();
    }

    /**
     * Create an instance of {@link CountEmployees }
     * 
     */
    public CountEmployees createCountEmployees() {
        return new CountEmployees();
    }

    /**
     * Create an instance of {@link AddEmployeeResponse }
     * 
     */
    public AddEmployeeResponse createAddEmployeeResponse() {
        return new AddEmployeeResponse();
    }

    /**
     * Create an instance of {@link EmployeeAlreadyExists }
     * 
     */
    public EmployeeAlreadyExists createEmployeeAlreadyExists() {
        return new EmployeeAlreadyExists();
    }

    /**
     * Create an instance of {@link GetEmployeeResponse }
     * 
     */
    public GetEmployeeResponse createGetEmployeeResponse() {
        return new GetEmployeeResponse();
    }

    /**
     * Create an instance of {@link DeleteEmployeeResponse }
     * 
     */
    public DeleteEmployeeResponse createDeleteEmployeeResponse() {
        return new DeleteEmployeeResponse();
    }

    /**
     * Create an instance of {@link GetAllEmployeesResponse }
     * 
     */
    public GetAllEmployeesResponse createGetAllEmployeesResponse() {
        return new GetAllEmployeesResponse();
    }

    /**
     * Create an instance of {@link UpdateEmployee }
     * 
     */
    public UpdateEmployee createUpdateEmployee() {
        return new UpdateEmployee();
    }

    /**
     * Create an instance of {@link CountEmployeesResponse }
     * 
     */
    public CountEmployeesResponse createCountEmployeesResponse() {
        return new CountEmployeesResponse();
    }

    /**
     * Create an instance of {@link GetEmployee }
     * 
     */
    public GetEmployee createGetEmployee() {
        return new GetEmployee();
    }

    /**
     * Create an instance of {@link DeleteEmployee }
     * 
     */
    public DeleteEmployee createDeleteEmployee() {
        return new DeleteEmployee();
    }

    /**
     * Create an instance of {@link UpdateEmployeeResponse }
     * 
     */
    public UpdateEmployeeResponse createUpdateEmployeeResponse() {
        return new UpdateEmployeeResponse();
    }

    /**
     * Create an instance of {@link AddEmployee }
     * 
     */
    public AddEmployee createAddEmployee() {
        return new AddEmployee();
    }

    /**
     * Create an instance of {@link GetAllEmployees }
     * 
     */
    public GetAllEmployees createGetAllEmployees() {
        return new GetAllEmployees();
    }

    /**
     * Create an instance of {@link Employee }
     * 
     */
    public Employee createEmployee() {
        return new Employee();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddEmployeeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bottomup.server.jaxws.baeldung.com/", name = "addEmployeeResponse")
    public JAXBElement<AddEmployeeResponse> createAddEmployeeResponse(AddEmployeeResponse value) {
        return new JAXBElement<AddEmployeeResponse>(_AddEmployeeResponse_QNAME, AddEmployeeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EmployeeAlreadyExists }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bottomup.server.jaxws.baeldung.com/", name = "EmployeeAlreadyExists")
    public JAXBElement<EmployeeAlreadyExists> createEmployeeAlreadyExists(EmployeeAlreadyExists value) {
        return new JAXBElement<EmployeeAlreadyExists>(_EmployeeAlreadyExists_QNAME, EmployeeAlreadyExists.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetEmployeeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bottomup.server.jaxws.baeldung.com/", name = "getEmployeeResponse")
    public JAXBElement<GetEmployeeResponse> createGetEmployeeResponse(GetEmployeeResponse value) {
        return new JAXBElement<GetEmployeeResponse>(_GetEmployeeResponse_QNAME, GetEmployeeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EmployeeNotFound }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bottomup.server.jaxws.baeldung.com/", name = "EmployeeNotFound")
    public JAXBElement<EmployeeNotFound> createEmployeeNotFound(EmployeeNotFound value) {
        return new JAXBElement<EmployeeNotFound>(_EmployeeNotFound_QNAME, EmployeeNotFound.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CountEmployees }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bottomup.server.jaxws.baeldung.com/", name = "countEmployees")
    public JAXBElement<CountEmployees> createCountEmployees(CountEmployees value) {
        return new JAXBElement<CountEmployees>(_CountEmployees_QNAME, CountEmployees.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateEmployee }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bottomup.server.jaxws.baeldung.com/", name = "updateEmployee")
    public JAXBElement<UpdateEmployee> createUpdateEmployee(UpdateEmployee value) {
        return new JAXBElement<UpdateEmployee>(_UpdateEmployee_QNAME, UpdateEmployee.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteEmployeeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bottomup.server.jaxws.baeldung.com/", name = "deleteEmployeeResponse")
    public JAXBElement<DeleteEmployeeResponse> createDeleteEmployeeResponse(DeleteEmployeeResponse value) {
        return new JAXBElement<DeleteEmployeeResponse>(_DeleteEmployeeResponse_QNAME, DeleteEmployeeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllEmployeesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bottomup.server.jaxws.baeldung.com/", name = "getAllEmployeesResponse")
    public JAXBElement<GetAllEmployeesResponse> createGetAllEmployeesResponse(GetAllEmployeesResponse value) {
        return new JAXBElement<GetAllEmployeesResponse>(_GetAllEmployeesResponse_QNAME, GetAllEmployeesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteEmployee }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bottomup.server.jaxws.baeldung.com/", name = "deleteEmployee")
    public JAXBElement<DeleteEmployee> createDeleteEmployee(DeleteEmployee value) {
        return new JAXBElement<DeleteEmployee>(_DeleteEmployee_QNAME, DeleteEmployee.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateEmployeeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bottomup.server.jaxws.baeldung.com/", name = "updateEmployeeResponse")
    public JAXBElement<UpdateEmployeeResponse> createUpdateEmployeeResponse(UpdateEmployeeResponse value) {
        return new JAXBElement<UpdateEmployeeResponse>(_UpdateEmployeeResponse_QNAME, UpdateEmployeeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddEmployee }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bottomup.server.jaxws.baeldung.com/", name = "addEmployee")
    public JAXBElement<AddEmployee> createAddEmployee(AddEmployee value) {
        return new JAXBElement<AddEmployee>(_AddEmployee_QNAME, AddEmployee.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllEmployees }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bottomup.server.jaxws.baeldung.com/", name = "getAllEmployees")
    public JAXBElement<GetAllEmployees> createGetAllEmployees(GetAllEmployees value) {
        return new JAXBElement<GetAllEmployees>(_GetAllEmployees_QNAME, GetAllEmployees.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CountEmployeesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bottomup.server.jaxws.baeldung.com/", name = "countEmployeesResponse")
    public JAXBElement<CountEmployeesResponse> createCountEmployeesResponse(CountEmployeesResponse value) {
        return new JAXBElement<CountEmployeesResponse>(_CountEmployeesResponse_QNAME, CountEmployeesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetEmployee }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bottomup.server.jaxws.baeldung.com/", name = "getEmployee")
    public JAXBElement<GetEmployee> createGetEmployee(GetEmployee value) {
        return new JAXBElement<GetEmployee>(_GetEmployee_QNAME, GetEmployee.class, null, value);
    }

}
