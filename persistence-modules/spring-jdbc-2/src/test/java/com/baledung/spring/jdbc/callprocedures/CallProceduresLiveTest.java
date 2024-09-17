package com.baledung.spring.jdbc.callprocedures;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.testcontainers.containers.PostgreSQLContainer;

@ExtendWith(SpringExtension.class)
@SpringJUnitConfig(TestConfiguration.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Sql(scripts = "/sp/create_stored_procedure.sql", config = @SqlConfig(dataSource = "datasource"))
public class CallProceduresLiveTest {

    Logger logger = LoggerFactory.getLogger(CallProceduresLiveTest.class);

    @Autowired
    private PostgreSQLContainer postgreSQLContainer;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @AfterAll
    void clean() {
        postgreSQLContainer.stop();
    }

    @Test
    void givenStoredProc_whenCallableStatement_thenExecProcUsingJdbcTemplateCallMethod() {

        List<SqlParameter> procedureParams = List.of(new SqlParameter("num1", Types.INTEGER),
            new SqlParameter("num2", Types.NUMERIC),
            new SqlOutParameter("result", Types.NUMERIC)
        );

        Map<String, Object> resultMap = jdbcTemplate.call(new CallableStatementCreator() {
            @Override
            public CallableStatement createCallableStatement(Connection con) throws SQLException {
                CallableStatement callableStatement = con.prepareCall("call sum_two_numbers(?, ?, ?)");
                callableStatement.registerOutParameter(3, Types.NUMERIC);
                callableStatement.setInt(1, 4);
                callableStatement.setInt(2, 5);

                return callableStatement;
            }
        }, procedureParams);
        logger.info("result: {}", resultMap.get("result"));
        assertEquals(new BigDecimal(9), resultMap.get("result"));
    }

    @Test
    void givenStoredProc_whenCallableStatement_thenExecProcUsingJdbcTemplateExecuteMethod() {
        String command = jdbcTemplate.execute(new CallableStatementCreator() {
            @Override
            public CallableStatement createCallableStatement(Connection con) throws SQLException {
                CallableStatement callableStatement = con.prepareCall("call sum_two_numbers(?, ?, ?)");
                callableStatement.registerOutParameter(3, Types.NUMERIC);
                return callableStatement;
            }
        }, new CallableStatementCallback<String>() {
            @Override
            public String doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {
                cs.setInt(1, 4);
                cs.setInt(2, 5);
                cs.execute();
                BigDecimal result = cs.getBigDecimal(3);
                assertEquals(new BigDecimal(9), result);
                String command = "4 + 5 = " + cs.getBigDecimal(3);
                return command;
            }
        });
        logger.info("command: {}", command);
        assertEquals("4 + 5 = 9", command);
    }

    @Test
    void givenStoredProc_whenJdbcTemplate_thenCreateSimpleJdbcCallAndExecProc() {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName("sum_two_numbers");

        Map<String, Integer> inParams = new HashMap<>();
        inParams.put("num1", 4);
        inParams.put("num2", 5);

        Map<String, Object> resultMap = simpleJdbcCall.execute(inParams);
        assertEquals(new BigDecimal(9), resultMap.get("result"));
    }

    @Test
    void givenStoredProc_whenJdbcTemplateAndDisableMetadata_thenCreateSimpleJdbcCallAndExecProc() {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
            .withProcedureName("sum_two_numbers")
            .withoutProcedureColumnMetaDataAccess();
        simpleJdbcCall.declareParameters(new SqlParameter("num1", Types.NUMERIC),
            new SqlParameter("num2", Types.NUMERIC),
            new SqlOutParameter("result", Types.NUMERIC));

        Map<String, Integer> inParams = new HashMap<>();
        inParams.put("num1", 4);
        inParams.put("num2", 5);
        Map<String, Object> resultMap = simpleJdbcCall.execute(inParams);
        assertEquals(new BigDecimal(9), resultMap.get("result"));
    }

    @Test
    void givenStoredProc_whenJdbcTemplate_thenCreateStoredProcedureAndExecProc() {
        StoredProcedure storedProcedure = new StoredProcedureImpl(jdbcTemplate, "sum_two_numbers");
        storedProcedure.declareParameter(new SqlParameter("num1", Types.NUMERIC));
        storedProcedure.declareParameter(new SqlParameter("num2", Types.NUMERIC));
        storedProcedure.declareParameter(new SqlOutParameter("result", Types.NUMERIC));
        Map<String, Integer> inParams = new HashMap<>();
        inParams.put("num1", 4);
        inParams.put("num2", 5);
        Map<String, Object> resultMap = storedProcedure.execute(inParams);
        assertEquals(new BigDecimal(9), resultMap.get("result"));
    }
}
