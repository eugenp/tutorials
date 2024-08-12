package com.baeldung.verticalslices.author.usecases;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.verticalslices.author.usecases.CreateArticleUseCase.CreateArticleRequest;

@RestController
@RequestMapping("content")
class ManageArticlesController {

    private final CreateArticleUseCase createArticle;
    private final EditArticleUseCase editArticle;
    private final DeleteArticleUseCase deleteArticle;
    private final ViewArticleUseCase viewArticle;

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
        deleteArticle.delete(slug);
    }

    @GetMapping("/{slug}")
    void viewArticle(@PathVariable String slug) {
        viewArticle.view(slug);
    }

    ManageArticlesController(CreateArticleUseCase createArticle, EditArticleUseCase editArticle, DeleteArticleUseCase deleteArticle, ViewArticleUseCase viewArticle) {
        this.createArticle = createArticle;
        this.editArticle = editArticle;
        this.deleteArticle = deleteArticle;
        this.viewArticle = viewArticle;
    }
}
