package com.baeldung.spring_security.service;

import com.baeldung.spring_security.dto.request.PostRequestDto;
import com.baeldung.spring_security.dto.response.PostResponseDto;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface IPostService {
    PostResponseDto create(PostRequestDto req, Authentication auth);
    void update(Long id, PostRequestDto dto, Authentication auth);
    void delete(Long id, Authentication auth);
    List<PostResponseDto> myPosts(Authentication auth);
}
