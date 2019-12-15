package com.baeldung.jackson.advancedannotations;

import com.fasterxml.jackson.databind.annotation.JsonAppend;

public class AppendBeans {
    public static class BeanWithoutAppend {
        private int id;
        private String name;

        public BeanWithoutAppend(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    @JsonAppend(attrs = { @JsonAppend.Attr(value = "version") })
    public static class BeanWithAppend {
        private int id;
        private String name;

        public BeanWithAppend(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}