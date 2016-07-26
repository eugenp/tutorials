package com.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.hateoas.MediaTypes;
import org.springframework.restdocs.RestDocumentation;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringRestDocsApplication.class)
@WebAppConfiguration
public class GettingStartedDocumentation {

    @Rule
    public final RestDocumentation restDocumentation = new RestDocumentation("target/generated-snippets");

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).apply(documentationConfiguration(this.restDocumentation)).alwaysDo(document("{method-name}/{step}/", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()))).build();
    }

    @Test
    public void index() throws Exception {
        this.mockMvc.perform(get("/").accept(MediaTypes.HAL_JSON)).andExpect(status().isOk()).andExpect(jsonPath("_links.crud", is(notNullValue()))).andExpect(jsonPath("_links.crud", is(notNullValue())));
    }

    // String createNote() throws Exception {
    // Map<String, String> note = new HashMap<String, String>();
    // note.put("title", "Note creation with cURL");
    // note.put("body", "An example of how to create a note using curl");
    // String noteLocation = this.mockMvc.perform(post("/crud")
    // .contentType(MediaTypes.HAL_JSON)
    // .content(objectMapper.writeValueAsString(note)))
    // .andExpect(status().isCreated())
    // .andExpect(header().string("Location", notNullValue()))
    // .andReturn()
    // .getResponse()
    // .getHeader("Location");
    // return noteLocation;
    // }
    //
    // MvcResult getNote(String noteLocation) throws Exception {
    // return this.mockMvc.perform(get(noteLocation))
    // .andExpect(status().isOk())
    // .andExpect(jsonPath("title", is(notNullValue())))
    // .andExpect(jsonPath("body", is(notNullValue())))
    // .andExpect(jsonPath("_links.crud", is(notNullValue())))
    // .andReturn();
    // }
    //
    //
    // String createTag() throws Exception, JsonProcessingException {
    // Map<String, String> tag = new HashMap<String, String>();
    // tag.put("name", "getting-started");
    // String tagLocation = this.mockMvc.perform(post("/crud")
    // .contentType(MediaTypes.HAL_JSON)
    // .content(objectMapper.writeValueAsString(tag)))
    // .andExpect(status().isCreated())
    // .andExpect(header().string("Location", notNullValue()))
    // .andReturn()
    // .getResponse()
    // .getHeader("Location");
    // return tagLocation;
    // }
    //
    // void getTag(String tagLocation) throws Exception {
    // this.mockMvc.perform(get(tagLocation)).andExpect(status().isOk())
    // .andExpect(jsonPath("name", is(notNullValue())))
    // .andExpect(jsonPath("_links.tagged-notes", is(notNullValue())));
    // }
    //
    // String createTaggedNote(String tag) throws Exception {
    // Map<String, Object> note = new HashMap<String, Object>();
    // note.put("title", "Tagged note creation with cURL");
    // note.put("body", "An example of how to create a tagged note using cURL");
    // note.put("tags", Arrays.asList(tag));
    //
    // String noteLocation = this.mockMvc.perform(post("/notes")
    // .contentType(MediaTypes.HAL_JSON)
    // .content(objectMapper.writeValueAsString(note)))
    // .andExpect(status().isCreated())
    // .andExpect(header().string("Location", notNullValue()))
    // .andReturn()
    // .getResponse()
    // .getHeader("Location");
    // return noteLocation;
    // }
    //
    // void getTags(String noteTagsLocation) throws Exception {
    // this.mockMvc.perform(get(noteTagsLocation))
    // .andExpect(status().isOk())
    // .andExpect(jsonPath("_embedded.tags", hasSize(1)));
    // }
    //
    // void tagExistingNote(String noteLocation, String tagLocation) throws Exception {
    // Map<String, Object> update = new HashMap<String, Object>();
    // update.put("tags", Arrays.asList(tagLocation));
    // this.mockMvc.perform(patch(noteLocation)
    // .contentType(MediaTypes.HAL_JSON)
    // .content(objectMapper.writeValueAsString(update)))
    // .andExpect(status().isNoContent());
    // }
    //
    // MvcResult getTaggedExistingNote(String noteLocation) throws Exception {
    // return this.mockMvc.perform(get(noteLocation)).andExpect(status().isOk()).andReturn();
    // }
    //
    // void getTagsForExistingNote(String noteTagsLocation) throws Exception {
    // this.mockMvc.perform(get(noteTagsLocation))
    // .andExpect(status().isOk()).andExpect(jsonPath("_embedded.tags", hasSize(1)));
    // }
    //
    // private String getLink(MvcResult result, String rel)
    // throws UnsupportedEncodingException {
    // return JsonPath.parse(result.getResponse().getContentAsString()).read("_links." + rel + ".href");
    // }

}
