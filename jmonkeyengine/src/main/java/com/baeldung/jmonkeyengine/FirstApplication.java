package com.baeldung.jmonkeyengine;

import com.jme3.app.SimpleApplication;
import com.jme3.system.AppSettings;

public class FirstApplication extends SimpleApplication {

    public static void main(String[] args) {
        FirstApplication app = new FirstApplication();
        app.start();
    }

    public FirstApplication() {
        super();

        AppSettings settings = new AppSettings(true);

        settings.setWidth(1024);
        settings.setHeight(768);
        settings.setCenterWindow(false);
        settings.setWindowXPosition(0);
        settings.setWindowYPosition(0);
        settings.setTitle("Our First Application");

        setSettings(settings);
    }

    @Override
    public void simpleInitApp() {
    }
}
