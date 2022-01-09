package com.baeldung.springpagination.controller;

import com.baeldung.springpagination.dto.PostDto;
import com.baeldung.springpagination.model.Post;
import com.baeldung.springpagination.service.IPostService;
import com.baeldung.springpagination.service.IUserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.text.ParseException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/posts")
public class PostRestController {
    
    @Autowired
    private IPostService postService;
 
    @Autowired
    private IUserService userService;
 
    @Autowired
    private ModelMapper modelMapper;
 
    @GetMapping
    @ResponseBody
    public List<PostDto> getPosts(
            @PathVariable("page") int page,
            @PathVariable("size") int size, 
            @PathVariable("sortDir") String sortDir, 
            @PathVariable("sort") String sort) {
        
        List<Post> posts = postService.getPostsList(page, size, sortDir, sort);
        return posts.stream()
          .map(this::convertToDto)
          .collect(Collectors.toList());
    }
 
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public PostDto createPost(@RequestBody PostDto postDto) throws ParseException {
        Post post = convertToEntity(postDto);
        Post postCreated = postService.createPost(post);
        return convertToDto(postCreated);
    }
 
    @GetMapping(value = "/{id}")
    @ResponseBody
    public PostDto getPost(@PathVariable("id") Long id) {
        return convertToDto(postService.getPostById(id));
    }
 
    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updatePost(@PathVariable("id") Long id, @RequestBody PostDto postDto) throws ParseException {
        if(!Objects.equals(id, postDto.getId())){
            throw new IllegalArgumentException("IDs don't match");
        }

        Post post = convertToEntity(postDto);
        postService.updatePost(post);
    }

    
    private PostDto convertToDto(Post post) {
        PostDto postDto = modelMapper.map(post, PostDto.class);
        postDto.setSubmissionDate(post.getSubmissionDate(), 
            userService.getCurrentUser().getPreference().getTimezone());
        return postDto;
    }
    
    private Post convertToEntity(PostDto postDto) throws ParseException {
        Post post = modelMapper.map(postDto, Post.class);
        post.setSubmissionDate(postDto.getSubmissionDateConverted(
          userService.getCurrentUser().getPreference().getTimezone()));
      
        if (postDto.getId() != null) {
            Post oldPost = postService.getPostById(postDto.getId());
            post.setRedditID(oldPost.getRedditID());
            post.setSent(oldPost.isSent());
        }
        return post;
    }
}
