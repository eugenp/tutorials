package com.baeldung.deepcopy2;

public class DataSource implements Cloneable {
    private String name;
    private ConnectionDetails connectionDetails;

    // constructors, getters and setters omitted

    public DataSource(String name, ConnectionDetails connectionDetails) {
        this.name = name;
        this.connectionDetails = connectionDetails;
    }

    public DataSource(DataSource that) {
        name = that.name;
        connectionDetails = new ConnectionDetails(that.connectionDetails);
    }

    @Override
    public DataSource clone() {
        try {
            DataSource clone = (DataSource) super.clone();
            clone.connectionDetails = this.connectionDetails.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ConnectionDetails getConnectionDetails() {
        return connectionDetails;
    }

    public void setConnectionDetails(ConnectionDetails connectionDetails) {
        this.connectionDetails = connectionDetails;
    }

}