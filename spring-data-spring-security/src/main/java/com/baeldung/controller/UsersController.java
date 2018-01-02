package com.baeldung.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.baeldung.data.repositories.TweetRepository;
import com.baeldung.data.repositories.UserRepository;
import com.baeldung.models.AppUser;
import com.baeldung.models.Tweet;
import com.baeldung.util.DummyContentUtil;

@Controller
public class UsersController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TweetRepository tweetRepository;

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public ModelAndView signup() {
        return new ModelAndView("signup");
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ModelAndView createUser(@RequestParam String name, @RequestParam String password, @RequestParam String email) {
        ModelAndView mv = null;
        if (name != null && email != null && password != null) {
            AppUser user = userRepository.save(new AppUser(name, email, new BCryptPasswordEncoder().encode(password)));
            if (user != null) {
                mv = new ModelAndView("redirect:login");
            }
        }
        if (mv == null) {
            mv = new ModelAndView("signup");
            mv.addObject("errorMessage", "User could not be created");
        }
        return mv;
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ModelAndView users(@RequestParam(defaultValue = "1") Integer page) {
        ModelAndView mv = new ModelAndView("users");
        Page<Tweet> pageLoaded = tweetRepository.getMyTweetsAndTheOnesILiked(new PageRequest(page-1, 5));
        mv.addObject("pageModel", pageLoaded);
        return mv;
    }

    @RequestMapping(value = "/loadContent", method = RequestMethod.GET)
    public ModelAndView loadDummy() {
        ModelAndView mv = new ModelAndView("riderct:users");
        List<AppUser> appUsers = (List<AppUser>) userRepository.save(DummyContentUtil.generateDummyUsers());
        tweetRepository.save(DummyContentUtil.generateDummyTweets(appUsers));
        return mv;
    }

}
