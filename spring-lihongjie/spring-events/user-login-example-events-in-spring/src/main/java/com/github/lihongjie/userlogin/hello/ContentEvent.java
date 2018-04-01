package com.github.lihongjie.userlogin.hello;

import org.springframework.context.ApplicationEvent;

public class ContentEvent extends ApplicationEvent {

	private static final long serialVersionUID = 8200945486752942012L;

	public ContentEvent(final String content) {
        super(content);
    }
}
