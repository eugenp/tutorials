package com.baeldung.jstl.foreachdemo;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class JSTLForEachDemoController {

    @RequestMapping(value = "/foreach-demo", method = RequestMethod.GET)
    public ModelAndView forEachDemo(final Model model) {
        ModelAndView mv = new ModelAndView("jstlForEachDemo");

        List<Movie> movies = List.of(
            //@formatter:off
            new Movie("The Hurt Locker", 2008),
            new Movie("A Beautiful Mind", 2001),
            new Movie("The Silence of the Lambs", 1991),
            new Movie("A Man for All Seasons", 1966),
            new Movie("No Country for Old Men", 2007)
            //@formatter:on
        );
        mv.addObject("movieList", movies);
        return mv;
    }
}