package org.baeldung.persistence.dao.rsql;

import cz.jirutka.rsql.parser.ast.ComparisonOperator;
import cz.jirutka.rsql.parser.ast.RSQLOperators;

public enum RsqlSearchOperation {
    EQUAL(RSQLOperators.EQUAL), NOT_EQUAL(RSQLOperators.NOT_EQUAL), GREATER_THAN(RSQLOperators.GREATER_THAN), GREATER_THAN_OR_EQUAL(RSQLOperators.GREATER_THAN_OR_EQUAL), LESS_THAN(RSQLOperators.LESS_THAN), LESS_THAN_OR_EQUAL(
            RSQLOperators.LESS_THAN_OR_EQUAL), IN(RSQLOperators.IN), NOT_IN(RSQLOperators.NOT_IN);

    private ComparisonOperator operator;

    private RsqlSearchOperation(final ComparisonOperator operator) {
        this.operator = operator;
    }

    public static RsqlSearchOperation getSimpleOperator(final ComparisonOperator operator) {
        for (final RsqlSearchOperation operation : values()) {
            if (operation.getOperator() == operator) {
                return operation;
            }
        }
        return null;
    }

    public ComparisonOperator getOperator() {
        return operator;
    }
}
