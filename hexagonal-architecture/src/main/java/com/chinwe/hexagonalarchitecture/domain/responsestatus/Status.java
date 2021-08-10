package com.chinwe.hexagonalarchitecture.domain.responsestatus;

import java.util.Objects;

public class Status {
    private int statusCode;
    private String description;

    public Status(int statusCode, String description) {
        this.statusCode = statusCode;
        this.description = description;
    }

    public Status() {
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Status status = (Status) o;
        return statusCode == status.statusCode &&
                Objects.equals(description, status.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(statusCode, description);
    }
}
