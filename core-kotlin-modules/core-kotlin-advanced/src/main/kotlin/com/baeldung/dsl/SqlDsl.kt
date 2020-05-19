package com.baeldung.dsl

abstract class Condition {

    fun and(initializer: Condition.() -> Unit) {
        addCondition(And().apply(initializer))
    }

    fun or(initializer: Condition.() -> Unit) {
        addCondition(Or().apply(initializer))
    }

    infix fun String.eq(value: Any?) {
        addCondition(Eq(this, value))
    }

    protected abstract fun addCondition(condition: Condition)
}

open class CompositeCondition(private val sqlOperator: String) : Condition() {
    private val conditions = mutableListOf<Condition>()

    override fun addCondition(condition: Condition) {
        conditions += condition
    }

    override fun toString(): String {
        return if (conditions.size == 1) {
            conditions.first().toString()
        } else {
            conditions.joinToString(prefix = "(", postfix = ")", separator = " $sqlOperator ") {
                "$it"
            }
        }
    }
}

class And : CompositeCondition("and")

class Or : CompositeCondition("or")

class Eq(private val column: String, private val value: Any?) : Condition() {

    init {
        if (value != null && value !is Number && value !is String) {
            throw IllegalArgumentException("Only <null>, numbers and strings values can be used in the 'where' clause")
        }
    }

    override fun addCondition(condition: Condition) {
        throw IllegalStateException("Can't add a nested condition to the sql 'eq'")
    }

    override fun toString(): String {
        return when (value) {
            null -> "$column is null"
            is String -> "$column = '$value'"
            else -> "$column = $value"
        }
    }
}

class SqlSelectBuilder {

    private val columns = mutableListOf<String>()
    private lateinit var table: String
    private var condition: Condition? = null

    fun select(vararg columns: String) {
        if (columns.isEmpty()) {
            throw IllegalArgumentException("At least one column should be defined")
        }
        if (this.columns.isNotEmpty()) {
            throw IllegalStateException("Detected an attempt to re-define columns to fetch. Current columns list: "
                                        + "${this.columns}, new columns list: $columns")
        }
        this.columns.addAll(columns)
    }

    fun from(table: String) {
        this.table = table
    }

    fun where(initializer: Condition.() -> Unit) {
        condition = And().apply(initializer)
    }

    fun build(): String {
        if (!::table.isInitialized) {
            throw IllegalStateException("Failed to build an sql select - target table is undefined")
        }
        return toString()
    }

    override fun toString(): String {
        val columnsToFetch =
                if (columns.isEmpty()) {
                    "*"
                } else {
                    columns.joinToString(", ")
                }
        val conditionString =
                if (condition == null) {
                    ""
                } else {
                    " where $condition"
                }
        return "select $columnsToFetch from $table$conditionString"
    }
}

fun query(initializer: SqlSelectBuilder.() -> Unit): SqlSelectBuilder {
    return SqlSelectBuilder().apply(initializer)
}