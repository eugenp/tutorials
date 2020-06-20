package com.baeldung.exceptiontesting.controller;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.baeldung.exceptiontesting.controller.ExceptionController;
import com.baeldung.exceptiontesting.exception.BadArgumentsException;
import com.baeldung.exceptiontesting.exception.InternalException;
import com.baeldung.exceptiontesting.exception.ResourceNotFoundException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
//@SpringBootTest(classes = Application.class)
@WebMvcTest(ExceptionController.class)
public class ExceptionControllerUnitTest{

    @Autowired
    private MockMvc mvc;

    @Test
    public void givenNotFound_whenGetSpecificException_thenNotFoundCode() throws Exception {
        String exceptionParam = "not_found";

        mvc.perform(get("/exception/{exception_id}", exceptionParam)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
            .andExpect(result -> assertEquals("resource not found", result.getResolvedException().getMessage()));
    }

    @Test
    public void givenBadArguments_whenGetSpecificException_thenBadRequest() throws Exception {
        String exceptionParam = "bad_arguments";

        mvc.perform(get("/exception/{exception_id}", exceptionParam)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(result -> assertTrue(result.getResolvedException() instanceof BadArgumentsException))
            .andExpect(result -> assertEquals("bad arguments", result.getResolvedException().getMessage()));
    }

    @Test
    public void givenOther_whenGetSpecificException_thenInternalServerError() throws Exception {
        String exceptionParam = "dummy";

        mvc.perform(get("/exception/{exception_id}", exceptionParam)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isInternalServerError())
            .andExpect(result -> assertTrue(result.getResolvedException() instanceof InternalException))
            .andExpect(result -> assertEquals("internal error", result.getResolvedException().getMessage()));
    }

    @Test(expected = Exception.class)
    public void whenGetException_thenInternalServerError() throws Exception {
        mvc.perform(get("/exception/throw")
            .contentType(MediaType.APPLICATION_JSON));
    }
}
