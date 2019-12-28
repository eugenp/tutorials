package com.baeldung.jackson.advancedannotations;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.baeldung.jackson.advancedannotations.AppendBeans.BeanWithAppend;
import com.baeldung.jackson.advancedannotations.AppendBeans.BeanWithoutAppend;
import com.baeldung.jackson.advancedannotations.IdentityReferenceBeans.BeanWithIdentityReference;
import com.baeldung.jackson.advancedannotations.IdentityReferenceBeans.BeanWithoutIdentityReference;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.factories.SchemaFactoryWrapper;

public class AdvancedAnnotationsUnitTest {
    @Test
    public void whenNotUsingJsonIdentityReferenceAnnotation_thenCorrect() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        BeanWithoutIdentityReference bean = new BeanWithoutIdentityReference(1, "Bean Without Identity Reference Annotation");
        String jsonString = mapper.writeValueAsString(bean);

        assertThat(jsonString, containsString("Bean Without Identity Reference Annotation"));
    }

    @Test
    public void whenUsingJsonIdentityReferenceAnnotation_thenCorrect() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        BeanWithIdentityReference bean = new BeanWithIdentityReference(1, "Bean With Identity Reference Annotation");
        String jsonString = mapper.writeValueAsString(bean);

        assertEquals("1", jsonString);
    }

    @Test
    public void whenNotUsingJsonAppendAnnotation_thenCorrect() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        BeanWithoutAppend bean = new BeanWithoutAppend(2, "Bean Without Append Annotation");
        ObjectWriter writer = mapper.writerFor(BeanWithoutAppend.class)
            .withAttribute("version", "1.0");
        String jsonString = writer.writeValueAsString(bean);

        assertThat(jsonString, not(containsString("version")));
        assertThat(jsonString, not(containsString("1.0")));
    }

    @Test
    public void whenUsingJsonAppendAnnotation_thenCorrect() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        BeanWithAppend bean = new BeanWithAppend(2, "Bean With Append Annotation");
        ObjectWriter writer = mapper.writerFor(BeanWithAppend.class)
            .withAttribute("version", "1.0");
        String jsonString = writer.writeValueAsString(bean);

        assertThat(jsonString, containsString("version"));
        assertThat(jsonString, containsString("1.0"));
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
        System.out.println(jsonString);
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