package com.baeldung.hexagonaljava;

import com.baeldung.hexagonaljava.adapters.driveradapters.TerminalAdapter;
import com.baeldung.hexagonaljava.adapters.drivenadapters.EmployeeFilePersistenceAdapter;
import com.baeldung.hexagonaljava.application.services.EmployeeServiceImpl;

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
