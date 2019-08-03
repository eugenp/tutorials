package org.baeldung.multipleentrypoints;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PagesController {

    @RequestMapping("/multipleHttpLinks")
    public String getMultipleHttpLinksPage() {
        return "multipleHttpElems/multipleHttpLinks";
    }

    @RequestMapping("/admin/myAdminPage")
    public String getAdminPage() {
        return "multipleHttpElems/myAdminPage";
    }

    @RequestMapping("/user/general/myUserPage")
    public String getUserPage() {
        return "multipleHttpElems/myUserPage";
    }
    
    @RequestMapping("/user/private/myPrivateUserPage")
    public String getPrivateUserPage() {
        return "multipleHttpElems/myPrivateUserPage";
    }

    @RequestMapping("/guest/myGuestPage")
    public String getGuestPage() {
        return "multipleHttpElems/myGuestPage";
    }

    @RequestMapping("/userLogin")
    public String getUserLoginPage() {
        return "multipleHttpElems/login";
    }
    
    @RequestMapping("/userLoginWithWarning")
    public String getUserLoginPageWithWarning() {
        return "multipleHttpElems/loginWithWarning";
    }

    @RequestMapping("/403")
    public String getAccessDeniedPage() {
        return "403";
    }
}
