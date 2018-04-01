package cn.nonocast.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class AccessToken {
	@JsonProperty
	private String secret;

	@JsonProperty
	private Long id;

	@JsonProperty
	private String email;

	@JsonProperty
	private LocalDateTime createdAt;

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public AccessToken() {
		this.secret = Long.toHexString(Double.doubleToLongBits(Math.random()));
	}

	public AccessToken(User user) {
		this();
		this.id = user.getId();
		this.email = user.getEmail();
		this.createdAt = LocalDateTime.now();
	}
}
