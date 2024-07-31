package com.baeldung.fluentinterface;

public class User {
	private String firstName;
	private String lastName;
	private String email;
	private String username;
	private Long id;

	public String name() {
		return firstName + " " + lastName;
	}

	public User(String firstName, String lastName, String email, String username, Long id) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.username = username;
		this.id = id;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private String firstName;
		private String lastName;
		private String email;
		private String username;
		private Long id;

		private Builder() {
		}

		public Builder firstName(String firstName) {
			this.firstName = firstName;
			return this;
		}

		public Builder lastName(String lastName) {
			this.lastName = lastName;
			return this;
		}

		public Builder email(String email) {
			this.email = email;
			return this;
		}

		public Builder username(String username) {
			this.username = username;
			return this;
		}

		public Builder id(Long id) {
			this.id = id;
			return this;
		}

		public User build() {
			return new User(firstName, lastName, email, username, id);
		}
	}
}
