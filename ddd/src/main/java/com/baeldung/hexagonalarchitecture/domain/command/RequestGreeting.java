package com.baeldung.hexagonalarchitecture.domain.command;

public class RequestGreeting {

        private String language;

        public RequestGreeting(String language) {
                this.language = language;
        }

        public String getLanguage() {
                return language;
        }
}
