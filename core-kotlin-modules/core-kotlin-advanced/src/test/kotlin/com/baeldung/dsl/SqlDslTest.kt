package com.baeldung.dsl

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class SqlDslTest {

    @Test
    fun `when no columns are specified then star is used`() {
        doTest("select * from table1") {
            from ("table1")
        }
    }

    @Test
    fun `when no condition is specified then correct query is built`() {
        doTest("select column1, column2 from table1") {
            select("column1", "column2")
            from ("table1")
        }
    }

    @Test(expected = Exception::class)
    fun `when no table is specified then an exception is thrown`() {
        query {
            select("column1")
        }.build()
    }

    @Test
    fun `when a list of conditions is specified then it's respected`() {
        doTest("select * from table1 where (column3 = 4 and column4 is null)") {
            from ("table1")
            where {
                "column3" eq 4
                "column4" eq null
            }
        }
    }

    @Test
    fun `when 'or' conditions are specified then they are respected`() {
        doTest("select * from table1 where (column3 = 4 or column4 is null)") {
            from ("table1")
            where {
                or {
                    "column3" eq 4
                    "column4" eq null
                }
            }
        }
    }

    @Test
    fun `when either 'and' or 'or' conditions are specified then they are respected`() {
        doTest("select * from table1 where ((column3 = 4 or column4 is null) and column5 = 42)") {
            from ("table1")
            where {
                and {
                    or {
                        "column3" eq 4
                        "column4" eq null
                    }
                    "column5" eq 42
                }
            }
        }
    }

    private fun doTest(expected: String, sql: SqlSelectBuilder.() -> Unit) {
        assertThat(query(sql).build()).isEqualTo(expected)
    }
}