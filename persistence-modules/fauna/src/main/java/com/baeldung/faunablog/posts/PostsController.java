package com.baeldung.faunablog.posts;

import com.faunadb.client.errors.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/posts")
public class PostsController {
    @Autowired
    private PostsService postsService;

    @GetMapping
    public List<Post> listPosts(@RequestParam(value = "author", required = false) String author) throws ExecutionException, InterruptedException {
        return author == null ? postsService.getAllPosts() :  postsService.getAuthorPosts("graham");
    }

    @GetMapping("/{id}")
    public Post getPost(@PathVariable("id") String id, @RequestParam(value = "before", required = false) Long before)
        throws ExecutionException, InterruptedException {
        return postsService.getPost(id, before);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("isAuthenticated()")
    public void createPost(@RequestBody UpdatedPost post) throws ExecutionException, InterruptedException {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        postsService.createPost(name, post.title(), post.content());
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("isAuthenticated()")
    public void updatePost(@PathVariable("id") String id, @RequestBody UpdatedPost post)
            throws ExecutionException, InterruptedException {
        postsService.updatePost(id, post.title(), post.content());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void postNotFound() {}
}
