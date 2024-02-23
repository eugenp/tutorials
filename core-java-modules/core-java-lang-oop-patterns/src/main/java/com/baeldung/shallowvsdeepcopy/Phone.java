package com.baeldung.shallowvsdeepcopy;

public class Phone {

    private App app;

    public Phone(App app) {
        this.app = app;
    }

    public Phone shallowCopy() {
        return new Phone(app);
    }

    public Phone deepCopy() {
        App copiedApp = new App(app.getLoggedInUser());
        return new Phone(copiedApp);
    }

    public App getApp() {
        return app;
    }
}
