package com.baeldung.dependency;

public class DataSource {

    private String driverClassName;
    private String url;
    private String username;
    private String password;

    public DataSource() {
    }

    // getters and setters

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DataSource{");
        sb.append("driverClassName='")
            .append(driverClassName)
            .append('\'');
        sb.append(", url='")
            .append(url)
            .append('\'');
        sb.append(", username='")
            .append(username)
            .append('\'');
        sb.append(", password='")
            .append(password)
            .append('\'');
        sb.append('}');
        return sb.toString();
    }
}
