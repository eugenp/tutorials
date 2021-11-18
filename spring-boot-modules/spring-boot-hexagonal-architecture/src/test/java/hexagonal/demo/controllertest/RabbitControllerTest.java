package hexagonal.demo.controllertest;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import hexagonal.demo.Adapter.RabbitController;
import hexagonal.demo.domain.RabbitStore;
import hexagonal.demo.port.RabbitService;

@WebMvcTest(RabbitController.class)
public class RabbitControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    @Autowired
    private RabbitService rabbitService;

    RabbitStore firstBreed = new RabbitStore(1, "Mimo", "Angora", 500, "full white with small dot", true);
    RabbitStore secondBreed = new RabbitStore(1, "Mio", "Dutch", 300, "half white and black", true);

    @Test
    public void getAllRabbits_inStore() throws Exception {
        List<RabbitStore> rabbits = Arrays.asList(firstBreed, secondBreed);
        Mockito.when(rabbitService.getRabbits())
            .thenReturn(rabbits);

        mvc.perform(MockMvcRequestBuilders.get("/api/rabbits")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(3)))
            .andExpect(jsonPath("$[1].name", is("Mimo")));

    }

    @Test
    public void getRabbits_byId() throws Exception {

        Mockito.when(rabbitService.getRabbitById(firstBreed.getId()))
            .thenReturn((firstBreed));

        mvc.perform(MockMvcRequestBuilders.get("/api/rabbits/1")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", notNullValue()))
            .andExpect(jsonPath("$.name", is(firstBreed.getName())));
    }

    @Test
    public void createRabbit_success() throws Exception {
        RabbitStore firstBreed = new RabbitStore(1, "Mimo", "Angora", 500, "full white with small dot", true);

        Mockito.when(rabbitService.newRabbit(firstBreed))
            .thenReturn(firstBreed);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/save")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(this.mapper.writeValueAsString(firstBreed));

        mvc.perform(mockRequest)
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", notNullValue()))
            .andExpect(jsonPath("$.name", is("Mimo")));
    }

    @Test
    public void updateRabbit_success() throws Exception {
        RabbitStore firstBreed = new RabbitStore(1, "vivo", "Angora", 500, "full white with small dot", true);

        Mockito.when(rabbitService.getRabbitById(firstBreed.getId()))
            .thenReturn(firstBreed);
        Mockito.when(rabbitService.newRabbit(firstBreed))
            .thenReturn(firstBreed);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/saveu")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(this.mapper.writeValueAsString(firstBreed));

        mvc.perform(mockRequest)
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", notNullValue()))
            .andExpect(jsonPath("$.name", is("Vivo")));
    }

    @Test
    public void deleteRabbit_success() throws Exception {
        Mockito.when(rabbitService.getRabbitById(firstBreed.getId()))
            .thenReturn(firstBreed);

        mvc.perform(MockMvcRequestBuilders.delete("/api/rabbits/1")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

    }

}
