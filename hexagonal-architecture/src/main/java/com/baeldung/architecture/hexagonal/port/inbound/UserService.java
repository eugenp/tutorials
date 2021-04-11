package com.baeldung.architecture.hexagonal.port.inbound;

import com.baeldung.architecture.hexagonal.domain.Post;
import com.baeldung.architecture.hexagonal.domain.PostOutDTO;
import com.baeldung.architecture.hexagonal.domain.User;
import com.baeldung.architecture.hexagonal.domain.UserOutDTO;

public interface UserService {

    UserOutDTO createUser(User user);

    UserOutDTO getUser(Integer userId);

    PostOutDTO createPost(Integer userId, Post post);

}
