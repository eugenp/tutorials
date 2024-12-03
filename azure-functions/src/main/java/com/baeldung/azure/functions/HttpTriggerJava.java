package com.baeldung.azure.functions;

import java.util.*;

import com.baeldung.azure.functions.http.entity.Employee;
import com.microsoft.azure.functions.annotation.*;
import com.microsoft.azure.functions.*;

/**
 * Azure Functions with HTTP Trigger.
 */
public class HttpTriggerJava {
    /**
     * This function listens at endpoint "/api/employee/{paritionKey/{rowKey}?code={Auth code}".
     * Way to invoke it using "curl" command in bash:
     * 1. curl -d "HTTP Body" /api/employee/{paritionKey/{rowKey}?code={Auth code}
     */
    @FunctionName("addEmployee")
    @StorageAccount("AZURE_STORAGE")
    public HttpResponseMessage run(@HttpTrigger(name = "req", methods = { HttpMethod.POST }, route = "employee/{partitionKey}/{rowKey}",
        authLevel = AuthorizationLevel.FUNCTION) HttpRequestMessage<Optional<Employee>> empRequest,
        @BindingName("partitionKey") String partitionKey,
        @BindingName("rowKey") String rowKey,
        @TableOutput(name = "data", tableName = "employee") OutputBinding<Employee> employeeOutputBinding,
        final ExecutionContext context) {
        context.getLogger().info("Received a http request: " + empRequest.getBody().toString());

        Employee employee = new Employee(empRequest.getBody().get().getName(),
            empRequest.getBody().get().getDepartment(),
            empRequest.getBody().get().getSex(),
            partitionKey, rowKey);
        employeeOutputBinding.setValue(employee);

        return empRequest.createResponseBuilder(HttpStatus.OK)
            .body("Employee Inserted")
            .build();
    }
}
