package com.example;

import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.restdocs.RestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringRestDocsApplication.class)
@WebAppConfiguration
public class ApiDocumentation {

	@Rule
	public final RestDocumentation restDocumentation = new RestDocumentation("target/generated-snippets");
	
	@Autowired
	private WebApplicationContext context;
	
	private RestDocumentationResultHandler document; 
	
	private MockMvc mockMvc;
	
	@Before
	public void setUp() {
		this.document = document("{method-name}",preprocessRequest(prettyPrint()),preprocessResponse(prettyPrint()));
		
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).apply(documentationConfiguration(this.restDocumentation)).alwaysDo(this.document).build();
	}
	
	@Test
	public void indexExample() throws Exception {
		this.document.snippets(links(linkWithRel("notes").description("The <<Simple descrition about the REST Service >>"),linkWithRel("tags").description("The <<resources-tags,Tags resource>>")),
				responseFields(fieldWithPath("_links").description("<<resources-index-links,Links>> to other resources")));
		
		this.mockMvc.perform(get("/api")).andExpect(status().isOk());
	}
	
	@Test
	public void contextLoads() {
	}
}