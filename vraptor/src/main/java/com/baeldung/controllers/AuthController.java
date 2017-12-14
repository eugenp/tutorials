package com.baeldung.controllers;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;
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

    @Path("/register")
    public void register(User user, HttpServletRequest request) {

      if(request.getMethod().equals("GET")) {
        result.use(FreemarkerView.class).withTemplate("auth/register");
      }
      else if(request.getMethod().equals("POST")) {
        validator.validate(user);
        validator.onErrorRedirectTo(this).register(user, request);
        if(!user.getPassword().equals(request.getParameter("password_confirmation"))) {
          result.include("error", "Passwords Do Not Match");
          result.redirectTo(this).register(null, request);
        }
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        Object resp = userDao.add(user);
        if(resp != null) {
          result.include("status", "Registration Successful! Now Login");
          result.redirectTo(this).login(request);
        } else {
          result.include("error", "There was an error during registration");
          result.redirectTo(this).register(user, request);
        }

      } else {
          result.notFound();
      }
    }

    @Path("/login")
    public void login(HttpServletRequest request) {

      if(request.getMethod().equals("GET")) {
        result.use(FreemarkerView.class).withTemplate("auth/login");
      }
      else if(request.getMethod().equals("POST")) {

        if (request.getParameter("user.email").isEmpty() || request.getParameter("user.password").isEmpty()) {
          result.include("error", "Email/Password is Required!");
          result.redirectTo(AuthController.class).login(request);
        }

        User user = userDao.findByEmail(request.getParameter("user.email"));
        if (Objects.nonNull(user) && BCrypt.checkpw(request.getParameter("user.password"), user.getPassword())) {
          userInfo.setUser(user);
          result.include("status", "Login Successful!");
          result.redirectTo(IndexController.class).index();
        } else {
          result.include("error", "Email/Password Does Not Match!");
          result.redirectTo(AuthController.class).login(request);
        }
      }
      else {
        result.include("error", "Invalid Request");
        result.notFound();
      }
    }
}
