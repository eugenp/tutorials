package com.baeldung.verticalslices.author.usecases;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.verticalslices.author.usecases.CreateArticleUseCase.CreateArticleRequest;

import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("content")
class ManageArticlesController {

    private final CreateArticleUseCase createArticle;
    private final EditArticleUseCase editArticle;
    private final DeleteArticleUseCase deleteArticle;

    @PostMapping
    void createArticle(@RequestBody CreateArticleRequest request) {
        createArticle.createArticle(request);
    }

    @PutMapping
    void editArticle(@RequestBody EditArticleUseCase.Request request) {
        editArticle.edit(request);
    }

    @DeleteMapping("/{slug}")
    void deleteArticle(@PathVariable String slug) {
        deleteArticle(slug);
    }

    ManageArticlesController(CreateArticleUseCase createArticle, EditArticleUseCase editArticle, DeleteArticleUseCase deleteArticle) {
        this.createArticle = createArticle;
        this.editArticle = editArticle;
        this.deleteArticle = deleteArticle;
    }
}
