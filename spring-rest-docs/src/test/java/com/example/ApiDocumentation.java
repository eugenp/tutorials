package com.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.hateoas.MediaTypes;
import org.springframework.restdocs.RestDocumentation;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.singletonList;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.util.StringUtils.collectionToDelimitedString;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringRestDocsApplication.class)
@WebAppConfiguration
public class ApiDocumentation {

    @Rule
    public final RestDocumentation restDocumentation = new RestDocumentation("target/generated-snippets");

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    private RestDocumentationResultHandler document;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.document = document("{method-name}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()));
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).apply(documentationConfiguration(this.restDocumentation)).alwaysDo(this.document).build();
    }

    @Test
    public void headersExample() throws Exception {
        this.document.snippets(responseHeaders(headerWithName("Content-Type").description("The Content-Type of the payload, e.g. `application/hal+json`")));
        this.mockMvc.perform(get("/")).andExpect(status().isOk());
    }

    @Test
    public void indexExample() throws Exception {
        this.document.snippets(links(linkWithRel("crud").description("The <<resources-tags,Tags resource>>")), responseFields(fieldWithPath("_links").description("<<resources-index-links,Links>> to other resources")));
        this.mockMvc.perform(get("/")).andExpect(status().isOk());
    }

    @Test
    public void crudGetExample() throws Exception {

        Map<String, String> tag = new HashMap<>();
        tag.put("name", "GET");

        String tagLocation = this.mockMvc.perform(get("/crud").contentType(MediaTypes.HAL_JSON).content(this.objectMapper.writeValueAsString(tag))).andExpect(status().isOk()).andReturn().getResponse().getHeader("Location");

        Map<String, Object> crud = new HashMap<>();
        crud.put("title", "Sample Model");
        crud.put("body", "http://www.baeldung.com/");
        crud.put("tags", singletonList(tagLocation));

        this.mockMvc.perform(get("/crud").contentType(MediaTypes.HAL_JSON).content(this.objectMapper.writeValueAsString(crud))).andExpect(status().isOk());
    }

    @Test
    public void crudCreateExample() throws Exception {
        Map<String, String> tag = new HashMap<>();
        tag.put("name", "CREATE");

        String tagLocation = this.mockMvc.perform(post("/crud").contentType(MediaTypes.HAL_JSON).content(this.objectMapper.writeValueAsString(tag))).andExpect(status().isCreated()).andReturn().getResponse().getHeader("Location");

        Map<String, Object> crud = new HashMap<>();
        crud.put("title", "Sample Model");
        crud.put("body", "http://www.baeldung.com/");
        crud.put("tags", singletonList(tagLocation));

        ConstrainedFields fields = new ConstrainedFields(CrudInput.class);
        this.document.snippets(requestFields(fields.withPath("title").description("The title of the note"), fields.withPath("body").description("The body of the note"), fields.withPath("tags").description("An array of tag resource URIs")));
        this.mockMvc.perform(post("/crud").contentType(MediaTypes.HAL_JSON).content(this.objectMapper.writeValueAsString(crud))).andExpect(status().isCreated());

    }

    @Test
    public void crudDeleteExample() throws Exception {

        Map<String, String> tag = new HashMap<>();
        tag.put("name", "DELETE");

        String tagLocation = this.mockMvc.perform(delete("/crud/10").contentType(MediaTypes.HAL_JSON).content(this.objectMapper.writeValueAsString(tag))).andExpect(status().isOk()).andReturn().getResponse().getHeader("Location");

        Map<String, Object> crud = new HashMap<>();
        crud.put("title", "Sample Model");
        crud.put("body", "http://www.baeldung.com/");
        crud.put("tags", singletonList(tagLocation));

        this.mockMvc.perform(delete("/crud/10").contentType(MediaTypes.HAL_JSON).content(this.objectMapper.writeValueAsString(crud))).andExpect(status().isOk());
    }

    @Test
    public void crudPatchExample() throws Exception {

        Map<String, String> tag = new HashMap<>();
        tag.put("name", "PATCH");

        String tagLocation = this.mockMvc.perform(patch("/crud/10").contentType(MediaTypes.HAL_JSON).content(this.objectMapper.writeValueAsString(tag))).andExpect(status().isNoContent()).andReturn().getResponse().getHeader("Location");

        Map<String, Object> crud = new HashMap<>();
        crud.put("title", "Sample Model");
        crud.put("body", "http://www.baeldung.com/");
        crud.put("tags", singletonList(tagLocation));

        this.mockMvc.perform(patch("/crud/10").contentType(MediaTypes.HAL_JSON).content(this.objectMapper.writeValueAsString(crud))).andExpect(status().isNoContent());
    }

    @Test
    public void crudPutExample() throws Exception {
        Map<String, String> tag = new HashMap<>();
        tag.put("name", "PUT");

        String tagLocation = this.mockMvc.perform(put("/crud/10").contentType(MediaTypes.HAL_JSON).content(this.objectMapper.writeValueAsString(tag))).andExpect(status().isAccepted()).andReturn().getResponse().getHeader("Location");

        Map<String, Object> crud = new HashMap<>();
        crud.put("title", "Sample Model");
        crud.put("body", "http://www.baeldung.com/");
        crud.put("tags", singletonList(tagLocation));

        this.mockMvc.perform(put("/crud/10").contentType(MediaTypes.HAL_JSON).content(this.objectMapper.writeValueAsString(crud))).andExpect(status().isAccepted());
    }

    @Test
    public void contextLoads() {
    }

    private static class ConstrainedFields {

        private final ConstraintDescriptions constraintDescriptions;

        ConstrainedFields(Class<?> input) {
            this.constraintDescriptions = new ConstraintDescriptions(input);
        }

        private FieldDescriptor withPath(String path) {
            return fieldWithPath(path).attributes(key("constraints").value(collectionToDelimitedString(this.constraintDescriptions.descriptionsForProperty(path), ". ")));
        }
    }

}
