package org.baeldung.scopes;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ScopesController {

	@Resource(name = "requestMessage")
	HelloMessageGenerator firstRequestMessage;

	@Resource(name = "requestMessage")
	HelloMessageGenerator secondRequestMessage;

	@Resource(name = "sessionMessage")
	HelloMessageGenerator firstSessionMessage;

	@Resource(name = "sessionMessage")
	HelloMessageGenerator secondSessionMessage;

	@RequestMapping("/scopes")
	public String getScopes() {
		return "scopesExample";
	}

	@RequestMapping("/scopes/firstRequest")
	public String getFirstRequest(final Model model) {
		firstRequestMessage.setMessage("Good morning!");
		firstSessionMessage.setMessage("Good afternoon!");
		model.addAttribute("requestMessage", firstRequestMessage.getMessage());
		model.addAttribute("sessionMessage", firstSessionMessage.getMessage());
		return "scopesFirstRequest";
	}

	@RequestMapping("/scopes/secondRequest")
	public String getSecondRequest(final Model model) {
		secondRequestMessage.setMessage("Good evening!");
		model.addAttribute("requestMessage", secondRequestMessage.getMessage());
		model.addAttribute("sessionMessage", secondSessionMessage.getMessage());
		return "scopesSecondRequest";
	}

}
