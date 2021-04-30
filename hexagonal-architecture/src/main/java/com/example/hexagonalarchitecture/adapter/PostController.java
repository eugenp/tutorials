package com.example.hexagonalarchitecture.adapter;

import com.example.hexagonalarchitecture.domain.Post;
import com.example.hexagonalarchitecture.port.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/post")
public class PostController {
    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<List<Post>> getAll() {
        return ResponseEntity.ok(postService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> readById(@PathVariable Integer id) {
        return ResponseEntity.ok(postService.getById(id));
    }

    @PostMapping
    public ResponseEntity<Post> create(@RequestBody Post post) {
        return ResponseEntity.ok(postService.create(post));
    }

    @PutMapping
    public ResponseEntity<Post> update(@RequestBody Post post) {
        return ResponseEntity.ok(postService.update(post));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Integer id) {
        postService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
