package com.baeldung.groovy.sql

import groovy.sql.GroovyResultSet
import groovy.sql.GroovyRowResult
import groovy.sql.Sql
import groovy.transform.CompileStatic
import static org.junit.Assert.*
import org.junit.Test

class SqlTest {

    final Map dbConnParams = [url: 'jdbc:hsqldb:mem:testDB', user: 'sa', password: '', driver: 'org.hsqldb.jdbc.JDBCDriver']

    @Test
    void whenNewSqlInstance_thenDbIsAccessed() {
        def sql = Sql.newInstance(dbConnParams)
        sql.close()
        sql.close()
    }

    @Test
    void whenTableDoesNotExist_thenSelectFails() {
        try {
            Sql.withInstance(dbConnParams) { Sql sql ->
                sql.eachRow('select * from PROJECT') {}
            }

            fail("An exception should have been thrown")
        } catch (ignored) {
            //Ok
        }
    }

    @Test
    void whenTableCreated_thenSelectIsPossible() {
        Sql.withInstance(dbConnParams) { Sql sql ->
            def result = sql.execute 'create table PROJECT_1 (id integer not null, name varchar(50), url varchar(100))'

            assertEquals(0, sql.updateCount)
            assertFalse(result)

            result = sql.execute('select * from PROJECT_1')

            assertTrue(result)
        }
    }

    @Test
    void whenIdentityColumn_thenInsertReturnsNewId() {
        Sql.withInstance(dbConnParams) { Sql sql ->
            sql.execute 'create table PROJECT_2 (ID IDENTITY, NAME VARCHAR (50), URL VARCHAR (100))'
            def ids = sql.executeInsert("INSERT INTO PROJECT_2 (NAME, URL) VALUES ('tutorials', 'github.com/eugenp/tutorials')")

            assertEquals(0, ids[0][0])

            ids = sql.executeInsert("INSERT INTO PROJECT_2 (NAME, URL) VALUES ('REST with Spring', 'github.com/eugenp/REST-With-Spring')")

            assertEquals(1, ids[0][0])
        }
    }

    @Test
    void whenUpdate_thenNumberOfAffectedRows() {
        Sql.withInstance(dbConnParams) { Sql sql ->
            sql.execute 'create table PROJECT_3 (ID IDENTITY, NAME VARCHAR (50), URL VARCHAR (100))'
            sql.executeInsert("INSERT INTO PROJECT_3 (NAME, URL) VALUES ('tutorials', 'github.com/eugenp/tutorials')")
            sql.executeInsert("INSERT INTO PROJECT_3 (NAME, URL) VALUES ('REST with Spring', 'github.com/eugenp/REST-With-Spring')")
            def count = sql.executeUpdate("UPDATE PROJECT_3 SET URL = 'https://' + URL")

            assertEquals(2, count)
        }
    }

    @Test
    void whenEachRow_thenResultSetHasProperties() {
        Sql.withInstance(dbConnParams) { Sql sql ->
            sql.execute 'create table PROJECT_4 (ID IDENTITY, NAME VARCHAR (50), URL VARCHAR (100))'
            sql.executeInsert("INSERT INTO PROJECT_4 (NAME, URL) VALUES ('tutorials', 'https://github.com/eugenp/tutorials')")
            sql.executeInsert("INSERT INTO PROJECT_4 (NAME, URL) VALUES ('REST with Spring', 'https://github.com/eugenp/REST-With-Spring')")

            sql.eachRow("SELECT * FROM PROJECT_4") { GroovyResultSet rs ->
                assertNotNull(rs.name)
                assertNotNull(rs.url)
                assertNotNull(rs[0])
                assertNotNull(rs[1])
                assertNotNull(rs[2])
                assertEquals(rs.name, rs['name'])
                assertEquals(rs.url, rs['url'])
            }
        }
    }

    @Test
    void whenPagination_thenSubsetIsReturned() {
        Sql.withInstance(dbConnParams) { Sql sql ->
            sql.execute 'create table PROJECT_5 (ID IDENTITY, NAME VARCHAR (50), URL VARCHAR (100))'
            sql.executeInsert("INSERT INTO PROJECT_5 (NAME, URL) VALUES ('tutorials', 'github.com/eugenp/tutorials')")
            sql.executeInsert("INSERT INTO PROJECT_5 (NAME, URL) VALUES ('REST with Spring', 'github.com/eugenp/REST-With-Spring')")
            def rows = sql.rows('SELECT * FROM PROJECT_5 ORDER BY NAME', 1, 1)

            assertEquals(1, rows.size())
            assertEquals('REST with Spring', rows[0].name)
        }
    }

    @Test
    void whenParameters_thenReplacement() {
        Sql.withInstance(dbConnParams) { Sql sql ->
            sql.execute 'create table PROJECT_6 (ID IDENTITY, NAME VARCHAR (50), URL VARCHAR (100))'
            sql.execute('INSERT INTO PROJECT_6 (NAME, URL) VALUES (?, ?)', 'tutorials', 'github.com/eugenp/tutorials')
            sql.execute("INSERT INTO PROJECT_6 (NAME, URL) VALUES (:name, :url)", [name: 'REST with Spring', url: 'github.com/eugenp/REST-With-Spring'])

            def rows = sql.rows("SELECT * FROM PROJECT_6 WHERE NAME = 'tutorials'")

            assertEquals(1, rows.size())

            rows = sql.rows("SELECT * FROM PROJECT_6 WHERE NAME = 'REST with Spring'")

            assertEquals(1, rows.size())
        }
    }

    @Test
    void whenParametersInGString_thenReplacement() {
        Sql.withInstance(dbConnParams) { Sql sql ->
            sql.execute 'create table PROJECT_7 (ID IDENTITY, NAME VARCHAR (50), URL VARCHAR (100))'
            sql.execute "INSERT INTO PROJECT_7 (NAME, URL) VALUES (${'tutorials'}, ${'github.com/eugenp/tutorials'})"
            def name = 'REST with Spring'
            def url = 'github.com/eugenp/REST-With-Spring'
            sql.execute "INSERT INTO PROJECT_7 (NAME, URL) VALUES (${name}, ${url})"

            def rows = sql.rows("SELECT * FROM PROJECT_7 WHERE NAME = 'tutorials'")

            assertEquals(1, rows.size())

            rows = sql.rows("SELECT * FROM PROJECT_7 WHERE NAME = 'REST with Spring'")

            assertEquals(1, rows.size())
        }
    }

    @Test
    void whenTransactionRollback_thenNoDataInserted() {
        Sql.withInstance(dbConnParams) { Sql sql ->
            sql.withTransaction {
                sql.execute 'create table PROJECT_8 (ID IDENTITY, NAME VARCHAR (50), URL VARCHAR (100))'
                sql.executeInsert("INSERT INTO PROJECT_8 (NAME, URL) VALUES ('tutorials', 'https://github.com/eugenp/tutorials')")
                sql.executeInsert("INSERT INTO PROJECT_8 (NAME, URL) VALUES ('REST with Spring', 'https://github.com/eugenp/REST-With-Spring')")
                sql.rollback()

                def rows = sql.rows("SELECT * FROM PROJECT_8")

                assertEquals(0, rows.size())
            }
        }
    }

    @Test
    void whenTransactionRollbackThenCommit_thenOnlyLastInserted() {
        Sql.withInstance(dbConnParams) { Sql sql ->
            sql.withTransaction {
                sql.execute 'create table PROJECT_9 (ID IDENTITY, NAME VARCHAR (50), URL VARCHAR (100))'
                sql.executeInsert("INSERT INTO PROJECT_9 (NAME, URL) VALUES ('tutorials', 'https://github.com/eugenp/tutorials')")
                sql.rollback()
                sql.executeInsert("INSERT INTO PROJECT_9 (NAME, URL) VALUES ('REST with Spring', 'https://github.com/eugenp/REST-With-Spring')")
                sql.rollback()
                sql.executeInsert("INSERT INTO PROJECT_9 (NAME, URL) VALUES ('tutorials', 'https://github.com/eugenp/tutorials')")
                sql.executeInsert("INSERT INTO PROJECT_9 (NAME, URL) VALUES ('REST with Spring', 'https://github.com/eugenp/REST-With-Spring')")
            }

            def rows = sql.rows("SELECT * FROM PROJECT_9")

            assertEquals(2, rows.size())
        }
    }

    @Test
    void whenException_thenTransactionIsRolledBack() {
        Sql.withInstance(dbConnParams) { Sql sql ->
            try {
                sql.withTransaction {
                    sql.execute 'create table PROJECT_10 (ID IDENTITY, NAME VARCHAR (50), URL VARCHAR (100))'
                    sql.executeInsert("INSERT INTO PROJECT_10 (NAME, URL) VALUES ('tutorials', 'https://github.com/eugenp/tutorials')")
                    throw new Exception('rollback')
                }
            } catch (ignored) {}

            def rows = sql.rows("SELECT * FROM PROJECT_10")

            assertEquals(0, rows.size())
        }
    }

    @Test
    void givenCachedConnection_whenException_thenDataIsPersisted() {
        Sql.withInstance(dbConnParams) { Sql sql ->
            try {
                sql.cacheConnection {
                    sql.execute 'create table PROJECT_11 (ID IDENTITY, NAME VARCHAR (50), URL VARCHAR (100))'
                    sql.executeInsert("INSERT INTO PROJECT_11 (NAME, URL) VALUES ('tutorials', 'https://github.com/eugenp/tutorials')")
                    throw new Exception('This does not rollback')
                }
            } catch (ignored) {}

            def rows = sql.rows("SELECT * FROM PROJECT_11")

            assertEquals(1, rows.size())
        }
    }

    /*@Test
    void whenModifyResultSet_thenDataIsChanged() {
        Sql.withInstance(dbConnParams) { Sql sql ->
            sql.execute 'create table PROJECT_5 (ID IDENTITY, NAME VARCHAR (50), URL VARCHAR (100))'
            sql.executeInsert("INSERT INTO PROJECT_5 (NAME, URL) VALUES ('tutorials', 'github.com/eugenp/tutorials')")
            sql.executeInsert("INSERT INTO PROJECT_5 (NAME, URL) VALUES ('REST with Spring', 'github.com/eugenp/REST-With-Spring')")

            sql.eachRow("SELECT * FROM PROJECT_5 FOR UPDATE ") { GroovyResultSet rs ->
                rs['name'] = "The great ${rs.name}!" as String
                rs.updateRow()
            }

            sql.eachRow("SELECT * FROM PROJECT_5") { GroovyResultSet rs ->
                assertTrue(rs.name.startsWith('The great '))
            }
        }
    }*/

}
