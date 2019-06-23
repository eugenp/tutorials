package com.baeldung.hexagonal.api.controller;

import com.baeldung.hexagonal.api.dto.PostDto;
import com.baeldung.hexagonal.core.domain.bo.PostBo;
import com.baeldung.hexagonal.core.ports.service.PostService;
import ma.glasnost.orika.BoundMapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/posts", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
class PostController {

    private PostService postService;
    private BoundMapperFacade<PostDto, PostBo> postMapper;

    @Autowired
    public PostController(PostService postService,
                          @Qualifier("postDtoMapper") BoundMapperFacade<PostDto, PostBo> mapper) {
        this.postService = postService;
        this.postMapper = mapper;
    }

    private static URI createUri(PostDto dto) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .buildAndExpand(dto.getId())
                .toUri();
    }

    @GetMapping
    public ResponseEntity<List<PostDto>> all() {
        List<PostDto> posts = this.postService.findAllPosts().stream()
                .map(this.postMapper::mapReverse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(posts);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> create(@RequestBody PostDto post) {

        PostDto saved = this.postMapper.mapReverse(
                this.postService.addNewPost(this.postMapper.map(post)));

        return ResponseEntity.created(createUri(saved)).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> get(@PathVariable("id") Long id) {
        PostDto post = this.postMapper.mapReverse(this.postService.findPostById(id));
        return ResponseEntity.ok(post);
    }

    @PostMapping("/{id}/submit")
    public ResponseEntity<Void> submit(@PathVariable("id") Long id) {
        this.postService.submitForReview(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/review")
    public ResponseEntity<Void> review(@PathVariable("id") Long id) {
        this.postService.review(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/publish")
    public ResponseEntity<Void> publish(@PathVariable("id") Long id) {
        this.postService.publish(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        this.postService.deletePostById(id);
        return ResponseEntity.ok().build();
    }
}
