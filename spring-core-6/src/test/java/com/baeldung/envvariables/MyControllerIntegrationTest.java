package com.baeldung.envvariables.valueinjection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(classes = MyController.class)
@AutoConfigureMockMvc
public class MyControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    /** NB : these tests are commented out because they are environment dependent
     * If you want to run one of them on your machine, follow the instruction above it
     *
     * expects the value of your system environment property 'OS' (it is already defined at least in Windows_NT)
     @Test void givenExistingSystemProperty_whenInjected_thenHasSystemPropertyValue() throws Exception {
     mockMvc.perform(get("/environment_name"))
     .andExpect(content().string(equalTo("Windows_NT")));
     }

      * expects the value of the JAVA_HOME environment variable (you need to define it if you haven't yet), with a + and the 'OS' environment property in the end
     @Test void givenCombinationOfSystemPropertyAndEnvironmentVariable_whenInjected_thenHasExpectedValue() throws Exception {
     mockMvc.perform(get("/java_home_and_environment"))
     .andExpect(content().string(equalTo("C:\\Program Files\\Java\\jdk-11.0.14+Windows_NT")));
     }

      * expects the content to be ${thispropertydoesnotexist} ; if you have defined an environment property called thispropertydoesnotexist, it would fail
     @Test void givenNonExistentProperty_whenInjected_thenKeepsTheStringValue() throws Exception {
     mockMvc.perform(get("/non_existent_property"))
     .andExpect(content().string(equalTo("${thispropertydoesnotexist}")));
     }
     */
}
