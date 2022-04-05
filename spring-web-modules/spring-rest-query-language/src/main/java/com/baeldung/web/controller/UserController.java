package com.baeldung.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.baeldung.persistence.dao.GenericSpecificationsBuilder;
import com.baeldung.persistence.dao.IUserDAO;
import com.baeldung.persistence.dao.MyUserPredicatesBuilder;
import com.baeldung.persistence.dao.MyUserRepository;
import com.baeldung.persistence.dao.UserRepository;
import com.baeldung.persistence.dao.UserSpecification;
import com.baeldung.persistence.dao.UserSpecificationsBuilder;
import com.baeldung.persistence.dao.rsql.CustomRsqlVisitor;
import com.baeldung.persistence.model.MyUser;
import com.baeldung.persistence.model.User;
import com.baeldung.web.util.CriteriaParser;
import com.baeldung.web.util.SearchCriteria;
import com.baeldung.web.util.SearchOperation;
import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;

//@EnableSpringDataWebSupport
@Controller
@RequestMapping(value = "/auth/")
public class UserController {

    @Autowired
    private IUserDAO service;

    @Autowired
    private UserRepository dao;

    @Autowired
    private MyUserRepository myUserRepository;

    public UserController() {
        super();
    }

    // API - READ

    @RequestMapping(method = RequestMethod.GET, value = "/users")
    @ResponseBody
    public List<User> search(@RequestParam(value = "search", required = false) String search) {
        List<SearchCriteria> params = new ArrayList<SearchCriteria>();
        if (search != null) {
            Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
            Matcher matcher = pattern.matcher(search + ",");
            while (matcher.find()) {
                params.add(new SearchCriteria(matcher.group(1), matcher.group(2), matcher.group(3)));
            }
        }
        return service.searchUser(params);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/users/spec")
    @ResponseBody
    public List<User> findAllBySpecification(@RequestParam(value = "search") String search) {
        UserSpecificationsBuilder builder = new UserSpecificationsBuilder();
        String operationSetExper = Joiner.on("|")
            .join(SearchOperation.SIMPLE_OPERATION_SET);
        Pattern pattern = Pattern.compile("(\\w+?)(" + operationSetExper + ")(\\p{Punct}?)(\\w+?)(\\p{Punct}?),");
        Matcher matcher = pattern.matcher(search + ",");
        while (matcher.find()) {
            builder.with(matcher.group(1), matcher.group(2), matcher.group(4), matcher.group(3), matcher.group(5));
        }

        Specification<User> spec = builder.build();
        return dao.findAll(spec);
    }

    @GetMapping(value = "/users/espec")
    @ResponseBody
    public List<User> findAllByOrPredicate(@RequestParam(value = "search") String search) {
        Specification<User> spec = resolveSpecification(search);
        return dao.findAll(spec);
    }

    @GetMapping(value = "/users/spec/adv")
    @ResponseBody
    public List<User> findAllByAdvPredicate(@RequestParam(value = "search") String search) {
        Specification<User> spec = resolveSpecificationFromInfixExpr(search);
        return dao.findAll(spec);
    }

    protected Specification<User> resolveSpecificationFromInfixExpr(String searchParameters) {
        CriteriaParser parser = new CriteriaParser();
        GenericSpecificationsBuilder<User> specBuilder = new GenericSpecificationsBuilder<>();
        return specBuilder.build(parser.parse(searchParameters), UserSpecification::new);
    }

    protected Specification<User> resolveSpecification(String searchParameters) {

        UserSpecificationsBuilder builder = new UserSpecificationsBuilder();
        String operationSetExper = Joiner.on("|")
            .join(SearchOperation.SIMPLE_OPERATION_SET);
        Pattern pattern = Pattern.compile("(\\p{Punct}?)(\\w+?)(" + operationSetExper + ")(\\p{Punct}?)(\\w+?)(\\p{Punct}?),");
        Matcher matcher = pattern.matcher(searchParameters + ",");
        while (matcher.find()) {
            builder.with(matcher.group(1), matcher.group(2), matcher.group(3), matcher.group(5), matcher.group(4), matcher.group(6));
        }
        return builder.build();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/myusers")
    @ResponseBody
    public Iterable<MyUser> findAllByQuerydsl(@RequestParam(value = "search") String search) {
        MyUserPredicatesBuilder builder = new MyUserPredicatesBuilder();
        if (search != null) {
            Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
            Matcher matcher = pattern.matcher(search + ",");
            while (matcher.find()) {
                builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
            }
        }
        BooleanExpression exp = builder.build();
        return myUserRepository.findAll(exp);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/users/rsql")
    @ResponseBody
    public List<User> findAllByRsql(@RequestParam(value = "search") String search) {
        Node rootNode = new RSQLParser().parse(search);
        Specification<User> spec = rootNode.accept(new CustomRsqlVisitor<User>());
        return dao.findAll(spec);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/api/myusers")
    @ResponseBody
    public Iterable<MyUser> findAllByWebQuerydsl(@QuerydslPredicate(root = MyUser.class) Predicate predicate) {
        return myUserRepository.findAll(predicate);
    }

    // API - WRITE

    @RequestMapping(method = RequestMethod.POST, value = "/users")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody User resource) {
        Preconditions.checkNotNull(resource);
        dao.save(resource);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/myusers")
    @ResponseStatus(HttpStatus.CREATED)
    public void addMyUser(@RequestBody MyUser resource) {
        Preconditions.checkNotNull(resource);
        myUserRepository.save(resource);

    }

}
