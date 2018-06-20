package com.baeldung.model;

import java.util.Date;

public class Data {

    private String uuid;
    private Date date;

    public Data(String id, Date date) {
        this.uuid = id;
        this.date = date;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Data data = (Data) o;

        if (uuid != null ? !uuid.equals(data.uuid) : data.uuid != null) {
            return false;
        }
        return date != null ? date.equals(data.date) : data.date == null;
    }

    @Override public int hashCode() {
        int result = uuid != null ? uuid.hashCode() : 0;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }

    @Override public String toString() {
        return "Data{" + "uuid=" + uuid + ", date=" + date + '}';
    }

}
