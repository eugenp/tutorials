package springsessionmongodb.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class SpringSessionMongoDBController {

    @GetMapping("/")
    public String count(HttpSession session) {

        Integer counter = (Integer) session.getAttribute("count");

        if (counter == null) {
            counter = 0;
        } else {
            counter += 1;
        }

        session.setAttribute("count", counter);

        return "<h1>Count is "+counter+"</h1>";
    }

}
