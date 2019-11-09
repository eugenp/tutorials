package com.baeldung.tapestry.pages;

import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.PasswordField;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;

public class Login {
	@Inject
	private Logger logger;

	@Inject
	private AlertManager alertManager;

	@InjectComponent
	private Form login;

	@InjectComponent("email")
	private TextField emailField;

	@InjectComponent("password")
	private PasswordField passwordField;

	@Property
	private String email;

	@Property
	private String password;

	void onValidateFromLogin() {
		if (!email.equals("users@tapestry.apache.org"))
			login.recordError(emailField, "Try with user: users@tapestry.apache.org");

		if ( !password.equals("Tapestry5"))
			login.recordError(passwordField, "Try with password: Tapestry5");
	}

	Object onSuccessFromLogin() {
		logger.info("Login successful!");
		alertManager.success("Welcome aboard!");

		return Index.class;
	}

	void onFailureFromLogin() {
		logger.warn("Login error!");
		alertManager.error("I'm sorry but I can't log you in!");
	}

}
