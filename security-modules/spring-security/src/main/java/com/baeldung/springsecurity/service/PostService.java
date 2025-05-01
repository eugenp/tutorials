package com.baeldung.spring_security.service;

import com.baeldung.springsecurity.dto.request.PostRequestDto;
import com.baeldung.springsecurity.dto.response.PostResponseDto;
import com.baeldung.springsecurity.entity.Post;
import com.baeldung.springsecurity.entity.User;
import com.baeldung.springsecurity.repository.PostRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final AuthService authService;

    public PostService(PostRepository postRepository, AuthService authService) {
        this.postRepository = postRepository;
        this.authService = authService;
    }

    public PostResponseDto create(PostRequestDto req, Authentication auth) {
        User user = authService.getUser(auth);
        Post post = new Post();
        post.setTitle(req.getTitle());
        post.setContent(req.getContent());
        post.setUser(user);
        return toDto(postRepository.save(post));
    }

    public void update(Long id, PostRequestDto dto, Authentication auth) {
        Post post = postRepository.findById(id).orElseThrow();
        if (!post.getUser().getUsername().equals(auth.getName())) {
            throw new AccessDeniedException("You can only edit your own posts");
        }
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        postRepository.save(post);
    }

    public void delete(Long id, Authentication auth) {
        boolean isAdmin = auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        Post post = postRepository.findById(id).orElseThrow();
        if (!isAdmin && !post.getUser().getUsername().equals(auth.getName())) {
            throw new AccessDeniedException("You can only delete your own posts");
        }
        postRepository.delete(post);
    }

    public List<PostResponseDto> myPosts(Authentication auth) {
        User user = authService.getUser(auth);
        return postRepository.findByUser(user).stream().map(this::toDto).toList();
    }

    private PostResponseDto toDto(Post post) {
        return new PostResponseDto(post.getId(), post.getTitle(), post.getContent(), post.getUser().getUsername());
    }
}
