package com.baeldung.service;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.baeldung.dto.BooleanObject;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ValidationServiceUnitTest {

    @Autowired
    ValidationService service;

    @Test
    void validateBoolean() {
        BooleanObject boolObj = new BooleanObject();
        boolObj.setBoolField(Boolean.valueOf(true));
        boolObj.setTrueField(Boolean.FALSE);
        Throwable throwableError = assertThrows(ConstraintViolationException.class, () -> {
            service.validateBoolean(boolObj);
        });
        assertTrue(throwableError.getLocalizedMessage()
            .endsWith("trueField must have true value"));
    }

}
