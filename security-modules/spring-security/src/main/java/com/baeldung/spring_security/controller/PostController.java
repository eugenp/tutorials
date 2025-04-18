package com.baeldung.spring_security.controller;

import com.baeldung.spring_security.dto.request.PostRequestDto;
import com.baeldung.spring_security.dto.response.PostResponseDto;
import com.baeldung.spring_security.service.IPostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("posts")
public class PostController {
    private final IPostService postService;

    public PostController(IPostService postService) {
        this.postService = postService;
    }

    @PostMapping("create")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<PostResponseDto> create(@RequestBody PostRequestDto dto, Authentication auth) {
        PostResponseDto result = postService.create(dto, auth);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("mine")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<PostResponseDto>> myPosts(Authentication auth) {
        List<PostResponseDto> result = postService.myPosts(auth);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody PostRequestDto req, Authentication auth) {
        try {
            postService.update(id, req, auth);
            return new ResponseEntity<>("updated", HttpStatus.OK);
        } catch (AccessDeniedException ade) {
            return new ResponseEntity<>(ade.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long id, Authentication auth) {
        try {
            postService.delete(id, auth);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (AccessDeniedException ade) {
            return new ResponseEntity<>(ade.getMessage(), HttpStatus.FORBIDDEN);
        } catch (NoSuchElementException nse) {
            return new ResponseEntity<>(nse.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
