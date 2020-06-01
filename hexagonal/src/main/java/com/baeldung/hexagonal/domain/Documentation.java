package com.baeldung.hexagonal.domain;

import java.util.Random;

public class Documentation {
        private long id;
        private String content;

        public Documentation() {
        }

        public Documentation(String content) {
                this.id = new Random().nextLong();
                this.content = content;
        }

        public Documentation(Long id, String content) {
                super();
                this.id = id;
                this.content = content;

        }

        public long getId() {
                return id;
        }

        //getters and setters
}
