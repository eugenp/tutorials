package cn.nonocast.admin.controller;

import cn.nonocast.admin.form.UserForm;
import cn.nonocast.model.User;
import cn.nonocast.service.UserService;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller("adminUserController")
@RequestMapping("/admin/users")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Pattern searchIdPattern  = Pattern.compile("^id:(\\d+)$", Pattern.CASE_INSENSITIVE);

    @RequestMapping("")
    public String users(Model model,
                        @RequestParam(required = false) String role,
                        @RequestParam(required = false) String q,
                        Pageable pageable) {
        Page<User> page = null;
        if (!Strings.isNullOrEmpty(q)) {
            Matcher m = searchIdPattern.matcher(q);
            if(m.matches()) {
                String ret = m.group(1);
                page = userService.findByKeyword(Long.valueOf(ret), pageable);
            }else {
                page = userService.findByKeyword(q, pageable);
            }
        } else if (!Strings.isNullOrEmpty(role)) {
            User.Role r = User.Role.valueOf(role.toUpperCase());
            if (r != null) {
                page = userService.findByRole(r, pageable);
            }
        } else {
            page = userService.findAll(pageable);
        }
        model.addAttribute("page", page);
        return "admin/user/index";
    }

    @RequestMapping("/create")
    public String newUser(@ModelAttribute("form") UserForm form) {
        return "admin/user/edit";
    }

    @RequestMapping("/{id:[0-9]+}/edit")
    public String edit(@PathVariable Long id, @Valid @ModelAttribute("form") UserForm form, Errors errors) {
        form.pull(userService.findOne(id));
        return "admin/user/edit";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute("form") UserForm form, Errors errors, Model model, RedirectAttributes redirectAttributes) {
        if(form.getPassword().length() < 6) {
            errors.rejectValue("password", "Size", "密码不少于6个字符");
        }

        if(userService.existsByEmail(form.getEmail())) {
            errors.rejectValue("email", "Duplication", "邮箱地址已被使用");
        }

        if (errors.hasErrors()) {
            return "admin/user/edit";
        }

        User user = null;
        try {
            user = new User();
            userService.save(form.push(user, passwordEncoder));
        } catch (DataAccessException ex) {
            return "admin/user/edit";
        }

        return "redirect:/admin/users?q=" + user.getName();
    }

    @RequestMapping(value="/{id:[0-9]+}")
    public String update(@PathVariable("id") Long id, @Valid @ModelAttribute("form") UserForm form, Errors errors) {
        if(!Strings.isNullOrEmpty(form.getPassword())) {
            if(form.getPassword().length() < 6) {
                errors.rejectValue("password", "Size", "密码不少于6个字符");
                return "admin/user/edit";
            }
        }

        if (errors.hasErrors()) {
            return "admin/user/edit";
        }

        User user = null;
        try {
            user = userService.findOne(id);

            if(!user.getEmail().equals(form.getEmail())) {
                if (userService.existsByEmail(form.getEmail())) {
                    errors.rejectValue("email", "Duplication", "邮箱地址已被使用");
                    return "admin/user/edit";
                }
            }

            form.push(user, passwordEncoder);
            userService.save(user);
        } catch (DataAccessException ex) {
            return "admin/user/edit";
        }

        return "redirect:/admin/users?q=" + user.getName();
    }

    @RequestMapping(value="/delete", method=RequestMethod.POST)
    public String delete(HttpServletRequest request) {
        List<Long> ids = Stream.of(request.getParameterValues("selected")).map(Long::valueOf).collect(Collectors.toList());
        List<User> users = userService.findByIds(ids);
        userService.delete(users);
        return "redirect:/admin/users";
    }
}
