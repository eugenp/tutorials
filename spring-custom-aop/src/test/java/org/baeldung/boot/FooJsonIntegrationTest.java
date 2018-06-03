package org.baeldung.boot;

import static org.assertj.core.api.Assertions.assertThat;

import org.baeldung.boot.model.Foo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@JsonTest
public class FooJsonIntegrationTest {

    @Autowired
    private JacksonTester<Foo> json;

    @Test
    public void testSerialize() throws Exception {
        Foo foo = new Foo(3, "Foo_Name_3");
        assertThat(this.json.write(foo)).isEqualToJson("expected.json");
        assertThat(this.json.write(foo)).hasJsonPathStringValue("@.name");
        assertThat(this.json.write(foo)).extractingJsonPathStringValue("@.name").isEqualTo("Foo_Name_3");
    }

    @Test
    public void testDeserialize() throws Exception {
        String content = "{\"id\":4,\"name\":\"Foo_Name_4\"}";
        assertThat(this.json.parseObject(content).getName()).isEqualTo("Foo_Name_4");
        assertThat(this.json.parseObject(content).getId() == 4);
    }
}