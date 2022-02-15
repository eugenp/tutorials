package com.baeldung.hexagonal;

import com.baeldung.hexagonal.adapters.driveradapters.TerminalAdapter;
import com.baeldung.hexagonal.adapters.drivenadapters.EmployeeFilePersistenceAdapter;
import com.baeldung.hexagonal.application.services.EmployeeServiceImpl;

/**
 * @author Hesam Ghiasi created on 2/15/22 
 */
public class Main {

    public static void main(String[] args) {
        EmployeeFilePersistenceAdapter employeeFilePersistenceService = new EmployeeFilePersistenceAdapter();
        EmployeeServiceImpl employeeService = new EmployeeServiceImpl(employeeFilePersistenceService);
        TerminalAdapter terminalAdapter = new TerminalAdapter(employeeService);
        terminalAdapter.addEmployee();
    }
}
