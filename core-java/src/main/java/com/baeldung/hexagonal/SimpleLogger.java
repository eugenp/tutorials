package com.baeldung.hexagonal;
public class SimpleLogger {

    private Display display;
    
    public SimpleLogger(Display logDisplay) {
        this.setDisplay(logDisplay);
    }

    public static SimpleLogger getInstance(Display logDisplay) {
        return new SimpleLogger(logDisplay);
    }
    
    public void info(String infoStr) {
        display.showMessage("INFO : " + infoStr);
    }
    
    public void warn(String warnStr) {
        display.showMessage("WARN : " + warnStr);
    }
    
    public void error(String errorMessage) {
        display.showMessage("ERROR : " + errorMessage);
    }

    public Display getDisplay() {
        return display;
    }

    public void setDisplay(Display display) {
        this.display = display;
    }
}