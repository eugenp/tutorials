package com.baeldung.querybuilder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.StringJoiner;

public class SqlQueryBuilder {

    public String buildStudentQueryUsingWhereOneEqualsOne(
            String enrollmentDate,
            String birthDate) {

        StringBuilder sb =
                new StringBuilder(
                        "SELECT id, name FROM Student WHERE 1=1");

        if (enrollmentDate != null) {
            sb.append(" AND enrollment_date = ?");
        }

        if (birthDate != null) {
            sb.append(" AND birth_date = ?");
        }

        return sb.toString();
    }


    public String buildStudentQueryUsingStringBuilder(
            String enrollmentDate,
            String birthDate) {

        StringBuilder sb =
                new StringBuilder(
                        "SELECT id, name FROM Student");

        if (enrollmentDate != null) {
            sb.append(" WHERE enrollment_date = ?");
        }

        if (birthDate != null) {
            sb.append(" AND birth_date = ?");
        }

        return sb.toString();
    }

    public PreparedStatement createPreparedStatement(
            Connection connection,
            String enrollmentDate,
            String birthDate) throws SQLException {

        String sql = buildStudentQueryUsingWhereOneEqualsOne(
                enrollmentDate,
                birthDate);

        PreparedStatement ps =
                connection.prepareStatement(sql);

        int parameterIndex = 1;

        if (enrollmentDate != null) {
            ps.setString(parameterIndex++, enrollmentDate);
        }

        if (birthDate != null) {
            ps.setString(parameterIndex++, birthDate);
        }

        return ps;
    }

    public String buildQueryUsingStringJoiner(
            boolean includeName,
            boolean includeId,
            boolean includeEnrollmentDate) {

        String baseQuery = "SELECT * FROM Student";

        StringJoiner whereClause =
                new StringJoiner(" AND ");

        if (includeName) {
            whereClause.add("name LIKE ?");
        }

        if (includeId) {
            whereClause.add("id = ?");
        }

        if (includeEnrollmentDate) {
            whereClause.add("enrollment_date = ?");
        }

        if (whereClause.length() > 0) {
            return baseQuery + " WHERE " + whereClause;
        }

        return baseQuery;
    }

}