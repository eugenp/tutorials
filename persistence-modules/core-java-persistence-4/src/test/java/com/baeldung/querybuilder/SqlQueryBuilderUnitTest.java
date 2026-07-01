package com.baeldung.querybuilder;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class SqlQueryBuilderUnitTest {

    private final SqlQueryBuilder queryBuilder =
            new SqlQueryBuilder();

    
    @Test
    void givenOnlyBirthDate_whenUsingStringBuilder_thenGenerateInvalidSql() {

        String query =
                queryBuilder.buildStudentQueryUsingStringBuilder(
                    null,"2000-01-01");

        assertEquals(
                "SELECT id, name FROM Student AND birth_date = ?",
                query);
    }
            

    @Test
    void givenNoFilters_whenUsingWhereOneEqualsOne_thenReturnBaseQuery() {

        String query =
                queryBuilder.buildStudentQueryUsingWhereOneEqualsOne(
                        null,
                        null);

        assertEquals(
                "SELECT id, name FROM Student WHERE 1=1",
                query);
    }

    @Test
    void givenEnrollmentDate_whenUsingWhereOneEqualsOne_thenAppendCondition() {

        String query =
                queryBuilder.buildStudentQueryUsingWhereOneEqualsOne(
                        "2024-01-01",
                        null);

        assertEquals(
                "SELECT id, name FROM Student WHERE 1=1 AND enrollment_date = ?",
                query);
    }

    @Test
    void givenAllFilters_whenUsingWhereOneEqualsOne_thenAppendAllConditions() {

        String query =
                queryBuilder.buildStudentQueryUsingWhereOneEqualsOne(
                        "2024-01-01",
                        "2000-01-01");

        assertEquals(
                "SELECT id, name FROM Student WHERE 1=1 AND enrollment_date = ? AND birth_date = ?",
                query);
    }

    @Test
    void givenSelectedFilters_whenUsingStringJoiner_thenBuildWhereClause() {

        String query =
                queryBuilder.buildQueryUsingStringJoiner(
                        true,
                        false,
                        true);

        assertEquals(
                "SELECT * FROM Student WHERE name LIKE ? AND enrollment_date = ?",
                query);
    }

    @Test
    void givenNoFilters_whenUsingStringJoiner_thenReturnBaseQuery() {

        String query =
                queryBuilder.buildQueryUsingStringJoiner(
                        false,
                        false,
                        false);

        assertEquals(
                "SELECT * FROM Student",
                query);
    }

}