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
public final class ToolIntegration {
  public final @NonNull String url;

  public final @NonNull String name;

  public final @NonNull String user;

  public final @NonNull String password;

  public ToolIntegration(@Named("url") @NonNull String url, @Named("name") @NonNull String name,
      @Named("user") @NonNull String user, @Named("password") @NonNull String password) {
    this.url = url;
    this.name = name;
    this.user = user;
    this.password = password;
  }

  public ToolIntegration withUrl(@NonNull String url) {
    return new ToolIntegration(url, name, user, password);
  }

  public ToolIntegration withName(@NonNull String name) {
    return new ToolIntegration(url, name, user, password);
  }

  public ToolIntegration withUser(@NonNull String user) {
    return new ToolIntegration(url, name, user, password);
  }

  public ToolIntegration withPassword(@NonNull String password) {
    return new ToolIntegration(url, name, user, password);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (this.getClass() != obj.getClass()) return false;
    ToolIntegration other = (ToolIntegration) obj;
    if (!Objects.equals(this.url, other.url)) return false;
    if (!Objects.equals(this.name, other.name)) return false;
    if (!Objects.equals(this.user, other.user)) return false;
    if (!Objects.equals(this.password, other.password)) return false;
    return true;
  }

  @Override
  public int hashCode() {
    int result = 1;
    result = 31 * result + Objects.hashCode(this.url);
    result = 31 * result + Objects.hashCode(this.name);
    result = 31 * result + Objects.hashCode(this.user);
    result = 31 * result + Objects.hashCode(this.password);
    return result;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder(250);
    builder.append(ToolIntegration.class.getSimpleName()).append(" {");
    appendProperty(builder, "url", this.url);
    appendProperty(builder, "name", this.name);
    appendProperty(builder, "user", this.user);
    appendProperty(builder, "password", this.password);
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
}
