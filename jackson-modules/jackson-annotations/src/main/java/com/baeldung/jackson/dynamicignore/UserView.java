package com.baeldung.jackson.dynamicignore;

import com.fasterxml.jackson.annotation.JsonView;

public class UserView {

    @JsonView(InternalView.class)
    private Long id;

    @JsonView(PublicView.class)
    private String name;

    public UserView(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public UserView() {
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    /**
     * View that is used to hide sensitive information
     */
    public interface PublicView {

    }

    /**
     * View that is used to show all information
     */
    public interface InternalView extends PublicView {

    }

}
