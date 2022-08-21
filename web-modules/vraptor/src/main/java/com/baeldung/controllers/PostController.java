package com.baeldung.controllers;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.freemarker.FreemarkerView;
import br.com.caelum.vraptor.validator.Validator;
import com.baeldung.config.UserInfo;
import com.baeldung.daos.PostDao;
import com.baeldung.models.Post;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.logging.Logger;

@Controller
public class PostController {

    private Result result;
    private Logger logger = Logger.getLogger(PostController.class.getName());
    private PostDao postDao;
    private UserInfo userInfo;
    private Validator validator;

    public PostController() {}

    @Inject
    public PostController(Result result, PostDao postDao, UserInfo userInfo, Validator validator) {
        this.result = result;
        this.postDao = postDao;
        this.userInfo = userInfo;
        this.validator = validator;
    }

    @Get("/post/add")
    public void addForm() {

        if(Objects.isNull(userInfo.getUser())) {
            result.include("error", "Please Login to Proceed");
            result.redirectTo(AuthController.class).loginForm();
            return;
        }

        result.use(FreemarkerView.class).withTemplate("posts/add");
    }

    @br.com.caelum.vraptor.Post("/post/add")
    public void add(Post post) {
        post.setAuthor(userInfo.getUser());
        validator.validate(post);
        if(validator.hasErrors())
            result.include("errors", validator.getErrors());
        validator.onErrorRedirectTo(this).addForm();

        Object id = postDao.add(post);

        if(Objects.nonNull(id)) {
            result.include("status", "Post Added Successfully");
            result.redirectTo(IndexController.class).index();
        } else {
            result.include(
              "error", "There was an error creating the post. Try Again");
            result.redirectTo(this).addForm();
        }
    }

    @Get("/posts/{id}")
    public void view(int id) {
        result.include("post", postDao.findById(id));
        result.use(FreemarkerView.class).withTemplate("view");
    }



}
