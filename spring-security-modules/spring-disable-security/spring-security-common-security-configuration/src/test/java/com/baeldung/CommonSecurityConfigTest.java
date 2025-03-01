import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import com.baeldung.DisableSpringSecurityApplication;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = DisableSpringSecurityApplication.class)
@AutoConfigureMockMvc
public class CommonSecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenAccessingAnyEndpoint_thenReturnOk() throws Exception {
        mockMvc.perform(get("/api/endpoint"))
                .andExpect(status().isOk());
    }
}