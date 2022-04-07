package com.baeldung.restdocopenapi.restdoc;

import com.baeldung.restdocopenapi.Foo;
import com.baeldung.restdocopenapi.FooController;
import com.baeldung.restdocopenapi.FooRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.util.StringUtils.collectionToDelimitedString;

@ExtendWith({ RestDocumentationExtension.class, SpringExtension.class })
@WebMvcTest(FooController.class)
class SpringRestDocsUnitTest {

    private MockMvc mockMvc;

    @MockBean
    private FooRepository fooRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .apply(documentationConfiguration(restDocumentation))
            .build();
    }

    @Test
    void whenGetAllFoo_thenSuccessful() throws Exception {
        when(fooRepository.findAll())
          .thenReturn(singletonList(new Foo(1, "Foo 1", "Foo 1")));

        this.mockMvc.perform(get("/foo"))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("Foo 1")))
            .andDo(document("getAllFoos"));
    }

    @Test
    void whenGetFooById_thenSuccessful() throws Exception {
        ConstraintDescriptions desc = new ConstraintDescriptions(Foo.class);

        when(fooRepository.findById(1L))
          .thenReturn(Optional.of(new Foo(1, "title", "body")));

        this.mockMvc.perform(get("/foo/{id}", 1))
            .andExpect(status().isOk())
            .andDo(document("getAFoo", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                pathParameters(parameterWithName("id").description("id of foo to be searched")),
                responseFields(fieldWithPath("id").description("The id of the foo" + collectionToDelimitedString(desc.descriptionsForProperty("id"), ". ")),
                fieldWithPath("title").description("The title of the foo"), fieldWithPath("body").description("The body of the foo"))));
    }

    @Test
    void whenPostFoo_thenSuccessful() throws Exception {
        Map<String, Object> foo = new HashMap<>();
        foo.put("id", 4L);
        foo.put("title", "New Foo");
        foo.put("body", "Body of New Foo");

        this.mockMvc.perform(post("/foo").contentType(MediaTypes.HAL_JSON)
            .content(this.objectMapper.writeValueAsString(foo)))
            .andExpect(status().isCreated())
            .andDo(document("createFoo", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()), requestFields(fieldWithPath("id").description("The id of the foo"), fieldWithPath("title").description("The title of the foo"),
                fieldWithPath("body").description("The body of the foo"))));
    }

    @Test
    void whenDeleteFoo_thenSuccessful() throws Exception {
        this.mockMvc.perform(delete("/foo/{id}", 2))
            .andExpect(status().isNoContent())
            .andDo(document("deleteFoo", pathParameters(parameterWithName("id").description("The id of the foo to delete"))));
    }

    @Test
    void whenUpdateFoo_thenSuccessful() throws Exception {

        when(fooRepository.existsById(3L)).thenReturn(true);
        when(fooRepository.save(any(Foo.class)))
          .thenReturn(new Foo(3, "Updated Foo", "Body of updated Foo"));

        ConstraintDescriptions desc = new ConstraintDescriptions(Foo.class);

        Map<String, Object> foo = new HashMap<>();
        foo.put("title", "Updated Foo");
        foo.put("body", "Body of Updated Foo");

        this.mockMvc.perform(put("/foo/{id}", 3).contentType(MediaTypes.HAL_JSON)
            .content(this.objectMapper.writeValueAsString(foo)))
            .andExpect(status().isOk())
            .andDo(document("updateFoo", pathParameters(parameterWithName("id").description("The id of the foo to update")),
                responseFields(fieldWithPath("id").description("The id of the updated foo" + collectionToDelimitedString(desc.descriptionsForProperty("id"), ". ")),
                    fieldWithPath("title").description("The title of the updated foo"), fieldWithPath("body").description("The body of the updated foo"))));
    }
}
