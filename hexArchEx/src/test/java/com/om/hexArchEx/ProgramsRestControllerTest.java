package com.om.hexArchEx;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//import javax.ws.rs.core.MediaType;

//import org.hamcrest.Matchers;
//import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(ProgramsRestControllerTest.class)
public class ProgramsRestControllerTest {
    @Autowired
    MockMvc mockMvc;
    
    @MockBean
    ProgramsService programService;
    
    Programs RECORD_1 = new Programs("Program1");
    Programs RECORD_2 = new Programs("Program2");
    Programs RECORD_3 = new Programs("Program3");

    
    @Test
    public void listPrograms_success() throws Exception {
    List<Programs> programs = new ArrayList<>(Arrays.asList(RECORD_1, RECORD_2, RECORD_3));
    
    Mockito.when(programService.listPrograms()).thenReturn(programs);
    
    mockMvc.perform(MockMvcRequestBuilders
            .get("/programsService/programs"));
    }
}
