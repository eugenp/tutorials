package com.baeldung.dependency;

public class DatabaseServiceWithoutAnnotations {

    private DataSource dataSource;

    public DatabaseServiceWithoutAnnotations(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DatabaseService{");
        sb.append("dataSource=")
            .append(dataSource);
        sb.append('}');
        return sb.toString();
    }
}
