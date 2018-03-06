package org.springframework.tutorial.tutorial5;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class HelloWorld1 implements InitializingBean,DisposableBean {

	private String message;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		// do some initialization work
		System.out.println("这里是初始化方法");
	}

	@Override
	public void destroy() throws Exception {
		// do some destruction work
		System.out.println("这里是销毁方法");
	}

	public void getMessage() {
		System.out.println(message);
	}
	
	public void setMessage(String message) {
		this.message = message;
	}

}
