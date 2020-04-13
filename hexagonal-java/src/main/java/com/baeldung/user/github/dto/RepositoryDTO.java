package com.baeldung.user.github.dto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * DTO for a Repository and its language details
 */
@JsonPropertyOrder({"RepoName", "languages"})
public class RepositoryDTO {
	
	/**
	 * Repository Name
	 */
	 @JsonProperty("RepoName")
    private String name;
	 /**
	  * List of languages used in the repository
	  */
    @JsonProperty("languages")
    private List<String> languages;
    
    /**
	 * Getters and Setters
	 */
    @JsonProperty("RepoName")
    public String getName() {
        return name;
    }

    @JsonProperty("RepoName")
    public void setName(final String name) {
        this.name = name;
    }

    @JsonProperty("languages")
    public List<String> getLanguages() {
        return languages;
    }

    @JsonProperty("languages")
    public void setLanguages(final List<String> languages) {
        this.languages = languages;
    }
}
