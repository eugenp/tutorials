package com.baeldung.restassured.assertjson;

import java.util.Objects;

public class WebsitePojo {
    public static class WebsiteText {
        private String language;
        private String code;

        public WebsiteText() {
        }

        public WebsiteText(String language, String code) {
            this.language = language;
            this.code = code;
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            WebsiteText that = (WebsiteText) o;
            return Objects.equals(language, that.language) && Objects.equals(code, that.code);
        }

        @Override
        public int hashCode() {
            return Objects.hash(language, code);
        }
    }

    private String name;
    private String type;
    private WebsiteText text;

    public WebsitePojo() {
    }

    public WebsitePojo(String name, String type, WebsiteText text) {
        this.name = name;
        this.type = type;
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public WebsiteText getText() {
        return text;
    }

    public void setText(WebsiteText text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WebsitePojo that = (WebsitePojo) o;
        return Objects.equals(name, that.name) && Objects.equals(type, that.type) && Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, text);
    }
}
