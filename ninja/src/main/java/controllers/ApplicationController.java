package controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;

import models.User;
import ninja.Context;
import ninja.Result;
import ninja.Results;
import ninja.i18n.Lang;
import ninja.i18n.Messages;
import ninja.jpa.UnitOfWork;
import ninja.session.FlashScope;
import ninja.validation.JSR303Validation;
import ninja.validation.Validation;
import services.UserService;

@Singleton
public class ApplicationController {
    
    @Inject
    Lang lang;

    @Inject
    Messages msg;
    
    private static Log logger = LogFactory.getLog(ApplicationController.class);
    
    @Inject 
    Provider<EntityManager> entitiyManagerProvider;

    @Inject
    UserService userService;
    
    public Result index() {
        return Results.html();
    }

    public Result userJson() {
        HashMap<String, String> userMap = userService.getUserMap();
        logger.info(userMap);
        return Results.json().render(userMap);
    }

    public Result helloWorld(Context context) {
        Optional<String> language = Optional.of("fr");        
        String helloMsg = msg.get("helloMsg", language).get();
        return Results.text().render(helloMsg);
    }
    
    public Result showFlashMsg(FlashScope flashScope) {
        flashScope.success("Success message");
        flashScope.error("Error message");
        return Results.redirect("/home");
    }
    
    public Result home() {
        return Results.html();
    }
    
    public Result createUser() {
        return Results.html();
    }
    
    @UnitOfWork
    public Result fetchUsers() {
        EntityManager entityManager = entitiyManagerProvider.get();
        Query q = entityManager.createQuery("SELECT x FROM User x");
        List<User> users = (List<User>) q.getResultList();
        return Results.json().render(users);
    }
    
    @Transactional
    public Result insertUser(FlashScope flashScope, @JSR303Validation User user, Validation validation) {
        logger.info("Inserting User : " +user);
            
        if (validation.getViolations().size() > 0) {
            flashScope.error("Validation Error: User can't be created");
        } else {
            EntityManager entityManager = entitiyManagerProvider.get();
            entityManager.persist(user);
            entityManager.flush();
            flashScope.success("User '" + user + "' is created successfully");
        }
        
        return Results.redirect("/home");
    }

}
