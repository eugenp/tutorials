package org.baeldung.jackson.annotation.extra;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.instanceOf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.factories.SchemaFactoryWrapper;

public class ExtraAnnotationTest {
    @Test
    public void whenUsingJsonIdentityReferenceAnnotation_thenCorrect() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        IdentityReferenceBean bean = new IdentityReferenceBean(1, "Identity Reference Bean");
        String jsonString = mapper.writeValueAsString(bean);

        assertEquals("1", jsonString);
    }

    @Test
    public void whenUsingJsonAppendAnnotation_thenCorrect() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        AppendBean bean = new AppendBean(2, "Append Bean");
        String jsonString = mapper.writeValueAsString(bean);

        assertThat(jsonString, containsString("appendedProperty"));
    }

    @Test
    public void whenUsingJsonNamingAnnotation_thenCorrect() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        NamingBean bean = new NamingBean(3, "Naming Bean");
        String jsonString = mapper.writeValueAsString(bean);

        assertThat(jsonString, containsString("bean_name"));
    }

    @Test
    public void whenUsingJsonPropertyDescriptionAnnotation_thenCorrect() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        SchemaFactoryWrapper wrapper = new SchemaFactoryWrapper();
        mapper.acceptJsonFormatVisitor(PropertyDescriptionBean.class, wrapper);
        JsonSchema jsonSchema = wrapper.finalSchema();
        String jsonString = mapper.writeValueAsString(jsonSchema);

        assertThat(jsonString, containsString("This is a description of the name property"));
    }

    @Test
    public void whenUsingJsonPOJOBuilderAnnotation_thenCorrect() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "{\"id\":5,\"name\":\"POJO Builder Bean\"}";
        POJOBuilderBean bean = mapper.readValue(jsonString, POJOBuilderBean.class);

        assertEquals(5, bean.getIdentity());
        assertEquals("POJO Builder Bean", bean.getBeanName());
    }

    @Test
    public void whenUsingJsonTypeIdAnnotation_thenCorrect() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enableDefaultTyping(DefaultTyping.NON_FINAL);
        TypeIdBean bean = new TypeIdBean(6, "Type Id Bean");
        String jsonString = mapper.writeValueAsString(bean);

        assertThat(jsonString, containsString("Type Id Bean"));
    }

    @Test
    public void whenUsingJsonTypeIdResolverAnnotation_thenCorrect() throws IOException {
        TypeIdResolverStructure.FirstBean bean1 = new TypeIdResolverStructure.FirstBean(1, "Bean 1");
        TypeIdResolverStructure.LastBean bean2 = new TypeIdResolverStructure.LastBean(2, "Bean 2");

        List<TypeIdResolverStructure.AbstractBean> beans = new ArrayList<>();
        beans.add(bean1);
        beans.add(bean2);

        TypeIdResolverStructure.BeanContainer serializedContainer = new TypeIdResolverStructure.BeanContainer();
        serializedContainer.setBeans(beans);
        
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(serializedContainer);
        assertThat(jsonString, containsString("bean1"));
        assertThat(jsonString, containsString("bean2"));

        TypeIdResolverStructure.BeanContainer deserializedContainer = mapper.readValue(jsonString, TypeIdResolverStructure.BeanContainer.class);
        List<TypeIdResolverStructure.AbstractBean> beanList = deserializedContainer.getBeans();
        assertThat(beanList.get(0), instanceOf(TypeIdResolverStructure.FirstBean.class));
        assertThat(beanList.get(1), instanceOf(TypeIdResolverStructure.LastBean.class));
    }
}