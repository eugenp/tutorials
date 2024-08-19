package com.baeldung.azure.functions;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.baeldung.azure.functions.blob.entity.Employee;
import com.microsoft.azure.functions.annotation.*;
import com.microsoft.azure.functions.*;

/**
 * Azure Functions with Azure Blob trigger.
 */
public class BlobTriggerJava {
    /**
     * This function will be invoked when a new or updated blob is detected at the specified path. The blob contents are provided as input to this function.
     */
    @FunctionName("BlobTriggerJava")
    @StorageAccount("AZURE_STORAGE")
    public void run(
        @BlobTrigger(name = "content", path = "feeds/{name}.csv", dataType = "binary") byte[] content,
        @BindingName("name") String fileName,
        @CosmosDBOutput(name = "output",
            databaseName = "organization",
            connection = "COSMOS_DB",
            containerName = "employee") OutputBinding<List<Employee>> employeeOutputBinding,
        final ExecutionContext context
    ) {
        context.getLogger().info("Java Blob trigger function processed a blob. Name: " + fileName + "\n  Size: " + content.length + " Bytes");
        employeeOutputBinding.setValue(getEmployeeList(content));
        context.getLogger().info("Processing finished");
    }

    private static List<Employee> getEmployeeList(byte[] content) {
        List<Employee> employees = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(content)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] record = line.split(",");
                employees.add(new Employee(record[0], record[1], record[2], record[3]));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return employees;
    }
}
