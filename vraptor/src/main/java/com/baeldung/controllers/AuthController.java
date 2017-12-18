package com.baeldung.controllers;

import br.com.caelum.vraptor.*;
import br.com.caelum.vraptor.freemarker.FreemarkerView;
import br.com.caelum.vraptor.validator.Validator;
import com.baeldung.config.UserInfo;
import com.baeldung.daos.UserDao;
import com.baeldung.models.User;
import org.mindrot.jbcrypt.BCrypt;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.logging.Logger;

@Controller
public class AuthController {

    private Validator validator;
    private UserDao userDao;
    private Result result;
    private UserInfo userInfo;
    private Logger logger = Logger.getLogger(getClass().getName());

    public AuthController() {
        this(null, null, null, null);
    }

    @Inject
    public AuthController(Validator validator, UserDao userDao, Result result, UserInfo userInfo) {
        this.validator = validator;
        this.userDao = userDao;
        this.result = result;
        this.userInfo = userInfo;
    }

    @Get("/register")
    public void registrationForm() {
        result.use(FreemarkerView.class).withTemplate("auth/register");
    }

    @Post("/register")
    public void register(User user, HttpServletRequest request) {

        validator.validate(user);

        if(validator.hasErrors()) {
            result.include("errors", validator.getErrors());
        }

        validator.onErrorRedirectTo(this).registrationForm();

        if(!user.getPassword()
             .equals(request.getParameter("password_confirmation"))) {
            result.include("error", "Passwords Do Not Match");
            result.redirectTo(this).registrationForm();
        }

        user.setPassword(
          BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));

        Object resp = userDao.add(user);

        if(resp != null) {
            result.include("status", "Registration Successful! Now Login");
            result.redirectTo(this).loginForm();
        } else {
            result.include("error", "There was an error during registration");
            result.redirectTo(this).registrationForm();
        }
    }

    @Get("/login")
    public void loginForm() {
        result.use(FreemarkerView.class).withTemplate("auth/login");
    }

    @Post("/login")
    public void login(HttpServletRequest request) {

        String password = request.getParameter("user.password");
        String email = request.getParameter("user.email");

        if(email.isEmpty() || password.isEmpty()) {
          result.include("error", "Email/Password is Required!");
          result.redirectTo(AuthController.class).loginForm();
        }

        User user = userDao.findByEmail(email);
        if(user != null && BCrypt.checkpw(password, user.getPassword())) {
          userInfo.setUser(user);
          result.include("status", "Login Successful!");
          result.redirectTo(IndexController.class).index();
        } else {
            result.include("error", "Email/Password Does Not Match!");
            result.redirectTo(AuthController.class).loginForm();
        }
    }
}
