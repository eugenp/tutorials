package com.baeldung.birtintegration.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Report DTO class
 */
public class Report {
    private String title;
    private String name;
    private List<Parameter> parameters;

    public String getTitle() {
        return title;
    }

    public Report() {
        super();
        parameters = new ArrayList<>();
    }

    public Report(String title, String name) {
        this();
        this.title = title;
        this.name = name;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }

    /**
     * Parameter DTO class
     *
     * @author koolshams
     */
    public static class Parameter {

        private String title;
        private String name;
        private ParameterType type;

        public Parameter() {
            super();
        }

        public Parameter(String title, String name, ParameterType type) {
            this();
            this.title = title;
            this.name = name;
            this.type = type;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public ParameterType getType() {
            return type;
        }

        public void setType(ParameterType type) {
            this.type = type;
        }

    }

    /**
     * Parameter Type ENUM, currently support INT and STRING
     *
     * @author koolshams
     */
    public enum ParameterType {
        INT, STRING;
    }
}
