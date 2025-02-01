package com.baeldung.spring.pkl;

import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuilder;
import java.util.Objects;
import org.pkl.config.java.mapper.Named;
import org.pkl.config.java.mapper.NonNull;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties
public final class ToolIntegrationProperties {
  private final @NonNull Connection gitConnection;

  private final @NonNull Connection jiraConnection;

  public ToolIntegrationProperties(@Named("gitConnection") @NonNull Connection gitConnection,
      @Named("jiraConnection") @NonNull Connection jiraConnection) {
    this.gitConnection = gitConnection;
    this.jiraConnection = jiraConnection;
  }

  public @NonNull Connection getGitConnection() {
    return gitConnection;
  }

  public ToolIntegrationProperties withGitConnection(@NonNull Connection gitConnection) {
    return new ToolIntegrationProperties(gitConnection, jiraConnection);
  }

  public @NonNull Connection getJiraConnection() {
    return jiraConnection;
  }

  public ToolIntegrationProperties withJiraConnection(@NonNull Connection jiraConnection) {
    return new ToolIntegrationProperties(gitConnection, jiraConnection);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (this.getClass() != obj.getClass()) return false;
    ToolIntegrationProperties other = (ToolIntegrationProperties) obj;
    if (!Objects.equals(this.gitConnection, other.gitConnection)) return false;
    if (!Objects.equals(this.jiraConnection, other.jiraConnection)) return false;
    return true;
  }

  @Override
  public int hashCode() {
    int result = 1;
    result = 31 * result + Objects.hashCode(this.gitConnection);
    result = 31 * result + Objects.hashCode(this.jiraConnection);
    return result;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder(150);
    builder.append(ToolIntegrationProperties.class.getSimpleName()).append(" {");
    appendProperty(builder, "gitConnection", this.gitConnection);
    appendProperty(builder, "jiraConnection", this.jiraConnection);
    builder.append("\n}");
    return builder.toString();
  }

  private static void appendProperty(StringBuilder builder, String name, Object value) {
    builder.append("\n  ").append(name).append(" = ");
    String[] lines = Objects.toString(value).split("\n");
    builder.append(lines[0]);
    for (int i = 1; i < lines.length; i++) {
      builder.append("\n  ").append(lines[i]);
    }
  }

  public static final class Connection {
    private final @NonNull String url;

    private final @NonNull String name;

    private final @NonNull Credential credential;

    public Connection(@Named("url") @NonNull String url, @Named("name") @NonNull String name,
        @Named("credential") @NonNull Credential credential) {
      this.url = url;
      this.name = name;
      this.credential = credential;
    }

    public @NonNull String getUrl() {
      return url;
    }

    public Connection withUrl(@NonNull String url) {
      return new Connection(url, name, credential);
    }

    public @NonNull String getName() {
      return name;
    }

    public Connection withName(@NonNull String name) {
      return new Connection(url, name, credential);
    }

    public @NonNull Credential getCredential() {
      return credential;
    }

    public Connection withCredential(@NonNull Credential credential) {
      return new Connection(url, name, credential);
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj) return true;
      if (obj == null) return false;
      if (this.getClass() != obj.getClass()) return false;
      Connection other = (Connection) obj;
      if (!Objects.equals(this.url, other.url)) return false;
      if (!Objects.equals(this.name, other.name)) return false;
      if (!Objects.equals(this.credential, other.credential)) return false;
      return true;
    }

    @Override
    public int hashCode() {
      int result = 1;
      result = 31 * result + Objects.hashCode(this.url);
      result = 31 * result + Objects.hashCode(this.name);
      result = 31 * result + Objects.hashCode(this.credential);
      return result;
    }

    @Override
    public String toString() {
      StringBuilder builder = new StringBuilder(200);
      builder.append(Connection.class.getSimpleName()).append(" {");
      appendProperty(builder, "url", this.url);
      appendProperty(builder, "name", this.name);
      appendProperty(builder, "credential", this.credential);
      builder.append("\n}");
      return builder.toString();
    }
  }

  public static final class Credential {
    private final @NonNull String user;

    private final @NonNull String password;

    public Credential(@Named("user") @NonNull String user,
        @Named("password") @NonNull String password) {
      this.user = user;
      this.password = password;
    }

    public @NonNull String getUser() {
      return user;
    }

    public Credential withUser(@NonNull String user) {
      return new Credential(user, password);
    }

    public @NonNull String getPassword() {
      return password;
    }

    public Credential withPassword(@NonNull String password) {
      return new Credential(user, password);
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj) return true;
      if (obj == null) return false;
      if (this.getClass() != obj.getClass()) return false;
      Credential other = (Credential) obj;
      if (!Objects.equals(this.user, other.user)) return false;
      if (!Objects.equals(this.password, other.password)) return false;
      return true;
    }

    @Override
    public int hashCode() {
      int result = 1;
      result = 31 * result + Objects.hashCode(this.user);
      result = 31 * result + Objects.hashCode(this.password);
      return result;
    }

    @Override
    public String toString() {
      StringBuilder builder = new StringBuilder(150);
      builder.append(Credential.class.getSimpleName()).append(" {");
      appendProperty(builder, "user", this.user);
      appendProperty(builder, "password", this.password);
      builder.append("\n}");
      return builder.toString();
    }
  }
}
