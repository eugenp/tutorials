package com.baeldung.bootique;

import com.baeldung.bootique.router.IndexController;
import com.google.inject.Module;

import io.bootique.Bootique;
import io.bootique.jersey.JerseyModule;

public class App {

	public static void main(String[] args) {
		Module module = binder -> JerseyModule.extend(binder).addResource(IndexController.class);
		Bootique.app(args).module(module).autoLoadModules().exec().exit();
	}

}
