package com.baeldung.vsa.author;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class ManageArticlesController {

    @PostMapping
    void createArticle() {
    }

    @PutMapping
    void editArticle() {
    }

    @DeleteMapping
    void deleteArticle() {
    }

}
