package com.baeldung.hexagonal.viewports.adapters;

import com.baeldung.hexagonal.viewports.UserInterfacePort;

public class UserInterfaceAdapter implements UserInterfacePort {

	private String viewName;

	@Override
	public void promptForCorrectData() {
		this.viewName = "/user/add-new";
	}

	@Override
	public void goToProfile() {
		this.viewName = "/user/{userId}";
	}

	public String getViewName() {
		return viewName;
	}
}
