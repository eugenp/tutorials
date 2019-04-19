package com.baeldung.modelmapper.controller;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.baeldung.modelmapper.dto.PostDto;
import com.baeldung.modelmapper.model.Post;
import com.baeldung.modelmapper.service.IPostService;
import com.baeldung.modelmapper.service.IUserService;

@Controller
public class PostRestController {
    
    @Autowired
    private IPostService postService;
 
    @Autowired
    private IUserService userService;
 
    @Autowired
    private ModelMapper modelMapper;
 
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<PostDto> getPosts(
            @PathVariable("page") int page,
            @PathVariable("size") int size, 
            @PathVariable("sortDir") String sortDir, 
            @PathVariable("sort") String sort) {
        
        List<Post> posts = postService.getPostsList(page, size, sortDir, sort);
        return posts.stream()
          .map(post -> convertToDto(post))
          .collect(Collectors.toList());
    }
 
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public PostDto createPost(@RequestBody PostDto postDto) throws ParseException {
        Post post = convertToEntity(postDto);
        Post postCreated = postService.createPost(post);
        return convertToDto(postCreated);
    }
 
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public PostDto getPost(@PathVariable("id") Long id) {
        return convertToDto(postService.getPostById(id));
    }
 
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void updatePost(@RequestBody PostDto postDto) throws ParseException {
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
