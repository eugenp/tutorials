package com.baeldung.thymeleaf.blog;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Random;

@Controller
public class BlogController {

    @GetMapping("/blog/new")
    public String newBlogPost(Model model) {
        // Set a random ID so we can see it in the HTML form
        BlogDTO blog = new BlogDTO();
        blog.setBlogId(Math.abs(new Random().nextLong() % 1000000));

        model.addAttribute("blog", blog);

        return "blog/blog-new";
    }

}
