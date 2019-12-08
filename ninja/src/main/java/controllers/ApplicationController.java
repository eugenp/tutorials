package controllers;

import ninja.Context;
import ninja.Result;
import ninja.Results;
import ninja.i18n.Lang;
import ninja.i18n.Messages;
import ninja.jpa.UnitOfWork;
import services.UserService;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.inject.Provider;
import com.google.inject.Singleton;

import models.User;
import com.google.inject.persist.Transactional;

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
        Result result = Results.html();
        lang.setLanguage("fr", result);
        return result;

    }

    public Result userJson() {
        User user = userService.getUser();
        logger.info(user);
        return Results.json().render(user);
    }

    public Result helloWorld(Context context) {
        Optional<String> language = Optional.of("en");        
        String helloMsg = msg.get("helloMsg", language).get();
        return Results.text().render(helloMsg);
    }
    
    @UnitOfWork
    public Result fetchUsers() {
        insertUser();
        EntityManager entityManager = entitiyManagerProvider.get();
        Query q = entityManager.createQuery("SELECT x FROM User x");
        List<User> users = (List<User>) q.getResultList();
        System.out.println(users);
        return Results.text().render("Done");
    }
    
    @Transactional
    public Result insertUser() {
        EntityManager entityManager = entitiyManagerProvider.get();
        User user = userService.getUser();
        entityManager.persist(user);
        entityManager.flush();
        
        return Results.text().render("Inserted");
    }

}
