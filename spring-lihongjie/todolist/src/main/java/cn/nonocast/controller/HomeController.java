package cn.nonocast.controller;

import cn.nonocast.model.User;
import cn.nonocast.service.TaskService;
import cn.nonocast.service.AccessTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("homeController")
@RequestMapping("/")
public class HomeController {
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private AccessTokenService accessTokenService;

	@Autowired
	private TaskService taskService;

	@Value("${project.version}")
	private String version;

	@Value("${spring.profiles.active}")
	private String profile;

	@RequestMapping("/home")
	public String home(@AuthenticationPrincipal User user, Model model) {
		model.addAttribute("version", this.version);
		model.addAttribute("profile", this.profile);
		model.addAttribute("token", accessTokenService.get(user).getSecret());
		model.addAttribute("tasks", taskService.findByUser(user));
		model.addAttribute("username", user.getName());
		model.addAttribute("avatar", user.getAvatar());

		return "home/index";
	}
}
