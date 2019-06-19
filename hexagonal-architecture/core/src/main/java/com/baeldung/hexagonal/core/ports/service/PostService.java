package com.baeldung.hexagonal.core.ports.service;

import com.baeldung.hexagonal.core.bo.PostBo;
import java.util.List;

public interface PostService {

  PostBo addNewPost(PostBo post);

  PostBo findPostById(String id);

  void deletePostById(String id);

  List<PostBo> findAllPosts();
}
