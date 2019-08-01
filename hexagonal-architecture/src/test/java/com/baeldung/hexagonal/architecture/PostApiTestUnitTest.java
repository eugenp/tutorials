package com.baeldung.hexagonal.architecture;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.baeldung.hexagonal.architecture.controller.PostRestApiControllerAdapter;
import com.baeldung.hexagonal.architecture.model.Post;
import com.baeldung.hexagonal.architecture.service.PostService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


@RunWith(SpringRunner.class)
@WebMvcTest(PostRestApiControllerAdapter.class)
@AutoConfigureMockMvc
public class PostApiTestUnitTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PostService service;

    @Test
    public void whenCreateNewPost_thenPostReturned() throws Exception {
        Post post = new Post();
        post.setMessage("Test");
        mvc.perform(post("/posts/new")
                .content("{\"message\": \"" + post.getMessage() + "\"}")
                .characterEncoding("utf-8")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}