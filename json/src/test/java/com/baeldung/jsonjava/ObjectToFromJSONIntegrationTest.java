package com.baeldung.jsonjava;

import org.json.JSONObject;
import org.junit.Test;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

public class ObjectToFromJSONIntegrationTest {

    @Test
    public void givenDemoBean_thenCreateJSONObject() {
        DemoBean demo = new DemoBean();
        demo.setId(1);
        demo.setName("lorem ipsum");
        demo.setActive(true);

        JSONObject jo = new JSONObject(demo);

        assertThatJson(jo)
          .isEqualTo("{\"name\":\"lorem ipsum\",\"active\":true,\"id\":1}");
    }
}
