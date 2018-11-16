package com.baeldung.hexagonal;

import java.io.File;

import com.baeldung.hexagonal.domain.EmployeeService;
import com.baeldung.hexagonal.output.EmployeeCsvWriter;
import com.baeldung.hexagonal.output.EmployeeLogger;
import com.baeldung.hexagonal.output.EmployeeOutput;
import com.baeldung.hexagonal.storage.EmployeeRepository;
import com.baeldung.hexagonal.storage.InMemoryEmployeeRepository;
import com.baeldung.hexagonal.ui.EmployeeConsoleInputImpl;
import com.baeldung.hexagonal.ui.EmployeeInput;

public class Application {

    public static void main(String[] args) {
        EmployeeRepository repository = new InMemoryEmployeeRepository();
        EmployeeOutput output = new EmployeeLogger();
        EmployeeOutput csvOutput = new EmployeeCsvWriter(new File(".").getAbsolutePath(), "output.csv");
        EmployeeService service = new EmployeeService(repository, csvOutput);
        EmployeeInput ui = new EmployeeConsoleInputImpl();
        ui.collectData(service);
        service.generateOutput();

    }

}
