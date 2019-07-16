package com.baeldung.eval;

public class Application {
	
	private UserInterface ui;
	private Database db;
	private Logger logger;
	
	public Application(UserInterface ui, Database db, Logger logger) {
		this.ui = ui;
		this.db = db;
		this.logger = logger;
	}
	
	public void doSomething() {
		db.store("foo", "bar");
		logger.log("Something");
		ui.notifyEventComplete("foo");
	}
}