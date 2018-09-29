package com.stackify.services;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import com.stackify.models.Employee;

import ch.qos.logback.classic.Level;

public class EmployeeServiceUnitTest {
    private static final Logger logger = LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);

    private EmployeeService employeeService = new EmployeeService();

    @Test
    public void testAppenders() {
        Logger rollingFileLogger = LoggerFactory.getLogger("rollingFileLogger");
        rollingFileLogger.info("Testing rolling file logger");

        MDC.put("userRole", "ADMIN");
        Logger siftingLogger = LoggerFactory.getLogger("roleSiftingLogger");
        siftingLogger.info("Admin Action");
    }

    @Test
    public void testLayouts() {
        Logger htmlLogger = LoggerFactory.getLogger("htmlLogger");
        htmlLogger.error("Employee Information Update Failed");
        htmlLogger.info("New Account Created");
        
        Logger colorLogger = LoggerFactory.getLogger("colorLogger");
        colorLogger.error("Employee Information Update Failed");
        colorLogger.info("New Account Created");
    }

    @Test
    public void testLogLevel() {
        ch.qos.logback.classic.Logger rollingFileLogger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger("rollingFileLogger");
        rollingFileLogger.setLevel(Level.DEBUG);
        rollingFileLogger.debug("Testing Log Level");
    }

    @Test
    public void testParameter() {
        Employee employee = new Employee("john@gmail.com", "John", 2000);
        if (logger.isDebugEnabled()) {
            logger.debug("The bonus for employee: " + employee.getName() + " is " + employeeService.calculateBonus(employee));
        }
        logger.debug("The bonus for employee {} is {}", employee.getName(), employeeService.calculateBonus(employee));
    }

    @Test
    public void testFilters() {
        Logger levelFilterLogger = LoggerFactory.getLogger("levelFilterLogger");
        levelFilterLogger.error("Employee Information Update Failed");
        Logger thresholdFilterLogger = LoggerFactory.getLogger("thresholdFilterLogger");
        thresholdFilterLogger.trace("Employee record inserted");
        Logger evaluatorFilterLogger = LoggerFactory.getLogger("evaluatorFilterLogger");
        evaluatorFilterLogger.debug("Employee account deactivated");
    }

    @Test
    public void testIgnoredLogger() {
        Logger colorLogger = LoggerFactory.getLogger("ignoredColorLogger");
        colorLogger.info("Ignored Log Message");
    }

    @Test
    public void testConditionalConfiguration() {
        logger.trace("Employee record updated");
    }
}
