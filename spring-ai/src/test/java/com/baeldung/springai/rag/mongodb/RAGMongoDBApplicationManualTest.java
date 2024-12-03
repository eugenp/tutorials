package com.baeldung.springai.rag.mongodb;

import com.baeldung.springai.rag.mongodb.config.VectorStoreConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.autoconfigure.mistralai.MistralAiAutoConfiguration;
import org.springframework.ai.autoconfigure.vectorstore.redis.RedisVectorStoreAutoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*
 * To set the test environment:
 * execute 'docker-compose up' in src/test/docker/mongodb
 * */
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@EnableAutoConfiguration(exclude = {RedisVectorStoreAutoConfiguration.class, MistralAiAutoConfiguration.class})
@SpringBootTest(classes = VectorStoreConfig.class)
class RAGMongoDBApplicationManualTest {
    private static Logger logger = LoggerFactory.getLogger(RAGMongoDBApplicationManualTest.class);

    @Autowired
    private MockMvc mockMvc;

    @Test
    void givenMongoDBVectorStore_whenCallingPostDocumentEndpoint_thenExpectedResponseCodeShouldBeReturned() throws Exception {
        mockMvc.perform(post("/wiki?filePath={filePath}",
          "src/test/resources/documentation/owl-documentation.md"))
          .andExpect(status().isCreated());

        mockMvc.perform(post("/wiki?filePath={filePath}",
          "src/test/resources/documentation/rag-documentation.md"))
          .andExpect(status().isCreated());
    }

    @Test
    void givenMongoDBVectorStoreWithDocuments_whenMakingSimilaritySearch_thenExpectedDocumentShouldBePresent() throws Exception {
        String responseContent = mockMvc.perform(get("/wiki?searchText={searchText}",
          "RAG Application"))
          .andExpect(status().isOk())
          .andReturn()
          .getResponse()
          .getContentAsString();

        assertThat(responseContent)
          .contains("RAG AI Application is responsible for storing the documentation");
    }

    @Test
    void givenMongoDBVectorStoreWithDocumentsAndLLMClient_whenAskQuestionAboutRAG_thenExpectedResponseShouldBeReturned() throws Exception {
        String responseContent = mockMvc.perform(get("/wiki/search?question={question}",
          "Explain the RAG Applications"))
          .andExpect(status().isOk())
          .andReturn()
          .getResponse()
          .getContentAsString();

        logger.atInfo().log(responseContent);

        assertThat(responseContent).isNotEmpty();
    }

    @Test
    void givenMongoDBVectorStoreWithDocumentsAndLLMClient_whenAskUnknownQuestion_thenExpectedResponseShouldBeReturned() throws Exception {
        String responseContent = mockMvc.perform(get("/wiki/search?question={question}",
          "Explain the Economic theory"))
          .andExpect(status().isOk())
          .andReturn()
          .getResponse()
          .getContentAsString();

        logger.atInfo().log(responseContent);

        assertThat(responseContent).isNotEmpty();
    }
}

