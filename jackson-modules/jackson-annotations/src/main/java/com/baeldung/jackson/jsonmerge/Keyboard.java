package com.baeldung.jackson.jsonmerge;

public class Keyboard {

    private String style;
    private String layout;

    public Keyboard(){
        super();
    }

    public Keyboard(String style, String layout) {
        this.style = style;
        this.layout = layout;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getLayout() {
        return layout;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    @Override
    public String toString() {
        return "Keyboard{" + "style='" + style + '\'' + ", layout='" + layout + '\'' + '}';
    }
}
