package org.baeldung.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.baeldung.persistence.dao.IUserDAO;
import org.baeldung.persistence.dao.MyUserPredicatesBuilder;
import org.baeldung.persistence.dao.MyUserRepository;
import org.baeldung.persistence.dao.UserRepository;
import org.baeldung.persistence.dao.UserSpecificationsBuilder;
import org.baeldung.persistence.dao.rsql.CustomRsqlVisitor;
import org.baeldung.persistence.model.MyUser;
import org.baeldung.persistence.model.User;
import org.baeldung.web.util.SearchCriteria;
import org.baeldung.web.util.SearchOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.mysema.query.types.Predicate;
import com.mysema.query.types.expr.BooleanExpression;

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
    public List<User> findAll(@RequestParam(value = "search", required = false) final String search) {
        final List<SearchCriteria> params = new ArrayList<SearchCriteria>();
        if (search != null) {
            final Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
            final Matcher matcher = pattern.matcher(search + ",");
            while (matcher.find()) {
                params.add(new SearchCriteria(matcher.group(1), matcher.group(2), matcher.group(3)));
            }
        }
        return service.searchUser(params);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/users/spec")
    @ResponseBody
    public List<User> findAllBySpecification(@RequestParam(value = "search") final String search) {
        final UserSpecificationsBuilder builder = new UserSpecificationsBuilder();
        final String operationSetExper = Joiner.on("|").join(SearchOperation.SIMPLE_OPERATION_SET);
        final Pattern pattern = Pattern.compile("(\\w+?)(" + operationSetExper + ")(\\p{Punct}?)(\\w+?)(\\p{Punct}?),");
        final Matcher matcher = pattern.matcher(search + ",");
        while (matcher.find()) {
            builder.with(matcher.group(1), matcher.group(2), matcher.group(4), matcher.group(3), matcher.group(5));
        }

        final Specification<User> spec = builder.build();
        return dao.findAll(spec);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/myusers")
    @ResponseBody
    public Iterable<MyUser> findAllByQuerydsl(@RequestParam(value = "search") final String search) {
        final MyUserPredicatesBuilder builder = new MyUserPredicatesBuilder();
        if (search != null) {
            final Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
            final Matcher matcher = pattern.matcher(search + ",");
            while (matcher.find()) {
                builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
            }
        }
        final BooleanExpression exp = builder.build();
        return myUserRepository.findAll(exp);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/users/rsql")
    @ResponseBody
    public List<User> findAllByRsql(@RequestParam(value = "search") final String search) {
        final Node rootNode = new RSQLParser().parse(search);
        final Specification<User> spec = rootNode.accept(new CustomRsqlVisitor<User>());
        return dao.findAll(spec);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/api/myusers")
    @ResponseBody
    public Iterable<MyUser> findAllByWebQuerydsl(@QuerydslPredicate(root = MyUser.class) final Predicate predicate) {
        return myUserRepository.findAll(predicate);
    }

    // API - WRITE

    @RequestMapping(method = RequestMethod.POST, value = "/users")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody final User resource) {
        Preconditions.checkNotNull(resource);
        dao.save(resource);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/myusers")
    @ResponseStatus(HttpStatus.CREATED)
    public void addMyUser(@RequestBody final MyUser resource) {
        Preconditions.checkNotNull(resource);
        myUserRepository.save(resource);

    }

}
