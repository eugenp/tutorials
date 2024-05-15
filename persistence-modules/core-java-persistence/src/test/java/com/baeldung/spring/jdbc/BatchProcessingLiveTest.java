package com.baeldung.spring.jdbc;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

@RunWith(MockitoJUnitRunner.class)
public class BatchProcessingLiveTest {


    @InjectMocks
    private BatchProcessing target = new BatchProcessing();

    @Mock
    private Connection connection;

    @Mock
    private Statement statement;

    @Mock
    private PreparedStatement employeeStatement;

    @Mock
    private PreparedStatement employeeAddressStatement;

    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void when_useStatement_thenInsertData_success() throws Exception {
        Mockito.when(connection.createStatement()).thenReturn(statement);
        target.useStatement();
    }

    @Test
    public void when_useStatement_ifThrowException_thenCatchException() throws Exception {
        Mockito.when(connection.createStatement()).thenThrow(new RuntimeException());
        target.useStatement();
    }

    @Test
    public void when_usePreparedStatement_thenInsertData_success() throws Exception {
        String insertEmployeeSQL = "INSERT INTO EMPLOYEE(ID, NAME, DESIGNATION) VALUES (?,?,?);";
        String insertEmployeeAddrSQL = "INSERT INTO EMP_ADDRESS(ID, EMP_ID, ADDRESS) VALUES (?,?,?);";
        Mockito.when(connection.prepareStatement(insertEmployeeSQL)).thenReturn(employeeStatement);
        Mockito.when(connection.prepareStatement(insertEmployeeAddrSQL)).thenReturn(employeeAddressStatement);
        target.usePreparedStatement();
    }

    @Test
    public void when_usePreparedStatement_ifThrowException_thenCatchException() throws Exception {
        String insertEmployeeSQL = "INSERT INTO EMPLOYEE(ID, NAME, DESIGNATION) VALUES (?,?,?);";
        String insertEmployeeAddrSQL = "INSERT INTO EMP_ADDRESS(ID, EMP_ID, ADDRESS) VALUES (?,?,?);";
        Mockito.when(connection.prepareStatement(insertEmployeeSQL)).thenReturn(employeeStatement);
        Mockito.when(connection.prepareStatement(insertEmployeeAddrSQL)).thenThrow(new RuntimeException());
        target.usePreparedStatement();
    }
}
