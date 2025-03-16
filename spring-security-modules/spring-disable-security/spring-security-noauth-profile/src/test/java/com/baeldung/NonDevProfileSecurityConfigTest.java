import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import com.baeldung.SpringSecurityAnnotationParameterApplication;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = SpringSecurityAnnotationParameterApplication.class)
@AutoConfigureMockMvc
public class NonDevProfileSecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenDevProfile_thenAccessIsPermitted() throws Exception {
        mockMvc.perform(get("/api/endpoint"))
                .andExpect(status().isUnauthorized());
    }
}