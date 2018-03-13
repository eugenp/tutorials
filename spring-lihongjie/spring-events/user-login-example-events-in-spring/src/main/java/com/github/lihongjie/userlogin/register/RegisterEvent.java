package com.github.lihongjie.userlogin.register;

import org.springframework.context.ApplicationEvent;

public class RegisterEvent extends ApplicationEvent {

	private static final long serialVersionUID = -9083142004348736918L;

	public RegisterEvent(User user) {
        super(user);
    }

}
