package com.baeldung.user.github.dto;

import java.util.Map;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * DTO for a user's Github Repository data
 */
@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
public class GitHubServiceResponseDTO {
	/**
	 * GitHub user id
	 */
	@JsonProperty("id")
	private Long id;
	
	/**
	 * GitHub user's username
	 */
	@JsonProperty("login")
	private String login;
	@JsonProperty("owner")
	private void unPackLoginFromNestedObj(Map<String, String> owner) {
		login = owner.get("login");
	}
	
	/**
	 * GitHub Repo name in the format username/reponame
	 */
	@JsonProperty("full_name")
    private String fullName;
	/**
	 * GitHub Repo name
	 */
	@JsonProperty("name")
    private String name;
	/**
	 * Html link to the Github Repository
	 */
	@JsonProperty("html_url")
    private String htmlUrl;
	/**
	 * Html link to the languages used for specific repository
	 */
	@JsonProperty("languages_url")
    private String languagesUrl;
	
	/**
	 * Getters and Setters
	 */
	
	@JsonProperty("full_name")
	public String getFullName() {
		return fullName;
	}
	@JsonProperty("full_name")
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	@JsonProperty("html_url")
	public String getHtmlUrl() {
		return htmlUrl;
	}
	@JsonProperty("html_url")
	public void setHtmlUrl(String htmlUrl) {
		this.htmlUrl = htmlUrl;
	}

	@JsonProperty("languages_url")
	public String getLanguagesUrl() {
		return languagesUrl;
	}
	
	@JsonProperty("languages_url")
	public void setLanguagesUrl(String languagesUrl) {
		this.languagesUrl = languagesUrl;
	}
	
    @JsonProperty("id")
	public Long getId() {
		return id;
	}
    
    @JsonProperty("id")
	public void setId(Long id) {
		this.id = id;
	}

    @JsonProperty("login")
	public String getLogin() {
		return login;
	}

    @JsonProperty("login")
	public void setLogin(String login) {
		this.login = login;
	}

    @JsonProperty("name")
	public String getName() {
		return name;
	}

    @JsonProperty("name")
	public void setName(String name) {
		this.name = name;
	}
    
}
