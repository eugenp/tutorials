package org.baeldung.multiplelogin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UsersController {

    @RequestMapping("/protectedLinks")
    public String getAnonymousPage() {
        return "protectedLinks";
    }

    @RequestMapping("/userPage")
    public String getUserPage() {
        return "userPage";
    }

    @RequestMapping("/adminPage")
    public String getAdminPage() {
        return "adminPage";
    }

    @RequestMapping("/loginAdmin")
    public String getAdminLoginPage() {
        return "loginAdmin";
    }

    @RequestMapping("/loginUser")
    public String getUserLoginPage() {
        return "loginUser";
    }

    @RequestMapping("/403")
    public String getAccessDeniedPage() {
        return "403";
    }
}
