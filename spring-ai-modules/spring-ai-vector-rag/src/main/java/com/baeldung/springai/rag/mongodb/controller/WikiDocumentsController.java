package com.baeldung.springai.rag.mongodb.controller;

import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.springai.rag.mongodb.dto.WikiDocument;
import com.baeldung.springai.rag.mongodb.service.WikiDocumentsServiceImpl;

@RestController
@RequestMapping("/wiki")
public class WikiDocumentsController {
    private final WikiDocumentsServiceImpl wikiDocumentsService;
    private final ChatClient chatClient;
    private final QuestionAnswerAdvisor questionAnswerAdvisor;

    public WikiDocumentsController(WikiDocumentsServiceImpl wikiDocumentsService,
                                   @Qualifier("openAiChatModel") ChatModel chatModel,
                                   QuestionAnswerAdvisor questionAnswerAdvisor) {
        this.wikiDocumentsService = wikiDocumentsService;
        this.questionAnswerAdvisor = questionAnswerAdvisor;
        this.chatClient = ChatClient.builder(chatModel).build();
    }

    @PostMapping
    public ResponseEntity<Void> saveDocument(@RequestParam("filePath") String filePath) {
        wikiDocumentsService.saveWikiDocument(filePath);

        return ResponseEntity.status(201).build();
    }
    @GetMapping
    public List<WikiDocument> get(@RequestParam("searchText") String searchText) {
        return wikiDocumentsService.findSimilarDocuments(searchText);
    }

    @GetMapping("/search")
    public String getWikiAnswer(@RequestParam("question") String question) {
        return chatClient.prompt()
          .user(question)
          .advisors(questionAnswerAdvisor)
          .call()
          .content();
    }
}
