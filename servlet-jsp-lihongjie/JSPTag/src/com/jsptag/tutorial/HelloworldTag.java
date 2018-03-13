package com.jsptag.tutorial;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * @description 自定义标签开发，在页面输出HelloWorld.
 * 		这是标签处理类，继承SimpleTagSupport父类，重写doTag方法，该方法在标签结束生成页面内容.
 * @date 2015-5-13 12:45:19
 * @author lhj
 * @version 1.0
 *
 */
public class HelloworldTag extends SimpleTagSupport {

	@Override
	public void doTag() throws JspException, IOException {
		//获取页面输出流，并输出字符串.
		getJspContext().getOut().write("Hello world , lihongjie");
		
	}

}
