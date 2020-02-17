package org.baeldung.multiplelogin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UsersController {

    @GetMapping("/protectedLinks")
    public String getAnonymousPage() {
        return "protectedLinks";
    }

    @GetMapping("/userPage")
    public String getUserPage() {
        return "userPage";
    }

    @GetMapping("/adminPage")
    public String getAdminPage() {
        return "adminPage";
    }

    @GetMapping("/loginAdmin")
    public String getAdminLoginPage() {
        return "loginAdmin";
    }

    @GetMapping("/loginUser")
    public String getUserLoginPage() {
        return "loginUser";
    }

    @GetMapping("/403")
    public String getAccessDeniedPage() {
        return "403";
    }
}
