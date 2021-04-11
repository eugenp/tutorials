package com.baeldung.architecture.hexagonal.usecase.business;

import com.baeldung.architecture.hexagonal.db.entity.PostEntity;
import com.baeldung.architecture.hexagonal.db.entity.UserEntity;
import com.baeldung.architecture.hexagonal.domain.Post;
import com.baeldung.architecture.hexagonal.domain.PostOutDTO;
import com.baeldung.architecture.hexagonal.domain.User;
import com.baeldung.architecture.hexagonal.domain.UserOutDTO;
import com.baeldung.architecture.hexagonal.port.inbound.UserService;
import com.baeldung.architecture.hexagonal.port.outbound.PostRepository;
import com.baeldung.architecture.hexagonal.port.outbound.UserRepository;
import com.baeldung.architecture.hexagonal.usecase.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public UserServiceImpl(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @Override
    public UserOutDTO createUser(User userInDTO) {
        UserEntity user = new UserEntity();
        user.setName(userInDTO.getName());
        userRepository.save(user);
        return mapUserToOutDto(user);
    }

    @Override
    public UserOutDTO getUser(Integer userId) {
        Optional<UserEntity> userOptional = userRepository.findById(userId);
        UserEntity user = userOptional.orElseThrow(NotFoundException::new);
        return mapUserToOutDto(user);
    }

    @Override
    public PostOutDTO createPost(Integer userId, Post postDTO) {
        Optional<UserEntity> userOptional = userRepository.findById(userId);
        UserEntity user = userOptional.orElseThrow(NotFoundException::new);
        PostEntity post = new PostEntity();
        post.setContents(postDTO.getContents());
        post.setUser(user);
        post.setTimeStamp(LocalDateTime.now());
        postRepository.save(post);
        return mapPostToOutDto(post, user);
    }

    private UserOutDTO mapUserToOutDto(UserEntity userEntity) {
        UserOutDTO userOutDTO = new UserOutDTO();
        userOutDTO.setId(userEntity.getId());
        userOutDTO.setName(userEntity.getName());
        return userOutDTO;
    }

    private PostOutDTO mapPostToOutDto(PostEntity post, UserEntity userEntity) {
        PostOutDTO postOutDTO = new PostOutDTO();
        postOutDTO.setContent(post.getContents());
        postOutDTO.setId(post.getId());
        postOutDTO.setCreatedBy(userEntity.getName());
        postOutDTO.setTimeStamp(post.getTimeStamp());
        return postOutDTO;
    }
}
