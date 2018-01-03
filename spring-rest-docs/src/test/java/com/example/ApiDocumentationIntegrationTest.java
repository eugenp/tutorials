package com.example;

import static java.util.Collections.singletonList;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.util.StringUtils.collectionToDelimitedString;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The Class ApiDocumentationIntegrationTest.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpringRestDocsApplication.class)
@WebAppConfiguration
public class ApiDocumentationIntegrationTest {

    /** The rest documentation. */
    @Rule
    public final JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("target/generated-snippets");

    /** The context. */
    @Autowired
    private WebApplicationContext context;

    /** The object mapper. */
    @Autowired
    private ObjectMapper objectMapper;

    /** The document. */
    private RestDocumentationResultHandler document;

    /** The mock mvc. */
    private MockMvc mockMvc;

    /**
     * Sets the up.
     */
    @Before
    public void setUp() {
        this.document = document("{method-name}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()));
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
            .apply(documentationConfiguration(this.restDocumentation))
            .alwaysDo(this.document)
            .build();
    }

    /**
     * Headers example.
     *
     * @throws Exception the exception
     */
    @Test
    public void headersExample() throws Exception {
        this.document.document(responseHeaders(headerWithName("Content-Type").description("The Content-Type of the payload, e.g. `application/hal+json`")));
        this.mockMvc.perform(get("/"))
            .andExpect(status().isOk());
    }

    /**
     * Index example.
     *
     * @throws Exception the exception
     */
    @Test
    public void indexExample() throws Exception {
        this.document.document(links(linkWithRel("crud").description("The <<resources-tags,Tags resource>>")), responseFields(fieldWithPath("_links").description("<<resources-index-links,Links>> to other resources")));
        this.mockMvc.perform(get("/"))
            .andExpect(status().isOk());
    }

    /**
     * Crud get example.
     *
     * @throws Exception the exception
     */
    @Test
    public void crudGetExample() throws Exception {

        Map<String, String> tag = new HashMap<>();
        tag.put("name", "GET");

        String tagLocation = this.mockMvc.perform(get("/crud").contentType(MediaTypes.HAL_JSON)
            .content(this.objectMapper.writeValueAsString(tag)))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getHeader("Location");

        Map<String, Object> crud = new HashMap<>();
        crud.put("title", "Sample Model");
        crud.put("body", "http://www.baeldung.com/");
        crud.put("tags", singletonList(tagLocation));

        this.mockMvc.perform(get("/crud").contentType(MediaTypes.HAL_JSON)
            .content(this.objectMapper.writeValueAsString(crud)))
            .andExpect(status().isOk());
    }

    /**
     * Crud create example.
     *
     * @throws Exception the exception
     */
    @Test
    public void crudCreateExample() throws Exception {
        Map<String, String> tag = new HashMap<>();
        tag.put("name", "CREATE");

        String tagLocation = this.mockMvc.perform(post("/crud").contentType(MediaTypes.HAL_JSON)
            .content(this.objectMapper.writeValueAsString(tag)))
            .andExpect(status().isCreated())
            .andReturn()
            .getResponse()
            .getHeader("Location");

        Map<String, Object> crud = new HashMap<>();
        crud.put("title", "Sample Model");
        crud.put("body", "http://www.baeldung.com/");
        crud.put("tags", singletonList(tagLocation));

        ConstrainedFields fields = new ConstrainedFields(CrudInput.class);
        this.document.document(requestFields(fields.withPath("title")
            .description("The title of the note"),
            fields.withPath("body")
                .description("The body of the note"),
            fields.withPath("tags")
                .description("An array of tag resource URIs")));
        this.mockMvc.perform(post("/crud").contentType(MediaTypes.HAL_JSON)
            .content(this.objectMapper.writeValueAsString(crud)))
            .andExpect(status().isCreated());

    }

    /**
     * Crud delete example.
     *
     * @throws Exception the exception
     */
    @Test
    public void crudDeleteExample() throws Exception {

        Map<String, String> tag = new HashMap<>();
        tag.put("name", "DELETE");

        String tagLocation = this.mockMvc.perform(delete("/crud/10").contentType(MediaTypes.HAL_JSON)
            .content(this.objectMapper.writeValueAsString(tag)))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getHeader("Location");

        Map<String, Object> crud = new HashMap<>();
        crud.put("title", "Sample Model");
        crud.put("body", "http://www.baeldung.com/");
        crud.put("tags", singletonList(tagLocation));

        this.mockMvc.perform(delete("/crud/10").contentType(MediaTypes.HAL_JSON)
            .content(this.objectMapper.writeValueAsString(crud)))
            .andExpect(status().isOk());
    }

    /**
     * Crud patch example.
     *
     * @throws Exception the exception
     */
    @Test
    public void crudPatchExample() throws Exception {

        Map<String, String> tag = new HashMap<>();
        tag.put("name", "PATCH");

        String tagLocation = this.mockMvc.perform(patch("/crud/10").contentType(MediaTypes.HAL_JSON)
            .content(this.objectMapper.writeValueAsString(tag)))
            .andExpect(status().isNoContent())
            .andReturn()
            .getResponse()
            .getHeader("Location");

        Map<String, Object> crud = new HashMap<>();
        crud.put("title", "Sample Model");
        crud.put("body", "http://www.baeldung.com/");
        crud.put("tags", singletonList(tagLocation));

        this.mockMvc.perform(patch("/crud/10").contentType(MediaTypes.HAL_JSON)
            .content(this.objectMapper.writeValueAsString(crud)))
            .andExpect(status().isNoContent());
    }

    /**
     * Crud put example.
     *
     * @throws Exception the exception
     */
    @Test
    public void crudPutExample() throws Exception {
        Map<String, String> tag = new HashMap<>();
        tag.put("name", "PUT");

        String tagLocation = this.mockMvc.perform(put("/crud/10").contentType(MediaTypes.HAL_JSON)
            .content(this.objectMapper.writeValueAsString(tag)))
            .andExpect(status().isAccepted())
            .andReturn()
            .getResponse()
            .getHeader("Location");

        Map<String, Object> crud = new HashMap<>();
        crud.put("title", "Sample Model");
        crud.put("body", "http://www.baeldung.com/");
        crud.put("tags", singletonList(tagLocation));

        this.mockMvc.perform(put("/crud/10").contentType(MediaTypes.HAL_JSON)
            .content(this.objectMapper.writeValueAsString(crud)))
            .andExpect(status().isAccepted());
    }

    /**
     * Context loads.
     */
    @Test
    public void contextLoads() {
    }

    /**
     * The Class ConstrainedFields.
     */
    private static class ConstrainedFields {

        /** The constraint descriptions. */
        private final ConstraintDescriptions constraintDescriptions;

        /**
         * Instantiates a new constrained fields.
         *
         * @param input the input
         */
        ConstrainedFields(Class<?> input) {
            this.constraintDescriptions = new ConstraintDescriptions(input);
        }

        /**
         * With path.
         *
         * @param path the path
         * @return the field descriptor
         */
        private FieldDescriptor withPath(String path) {
            return fieldWithPath(path).attributes(key("constraints").value(collectionToDelimitedString(this.constraintDescriptions.descriptionsForProperty(path), ". ")));
        }
    }

}
