package org.baeldung.persistence.dao.rsql;

import cz.jirutka.rsql.parser.ast.ComparisonNode;
import cz.jirutka.rsql.parser.ast.LogicalNode;
import cz.jirutka.rsql.parser.ast.LogicalOperator;
import cz.jirutka.rsql.parser.ast.Node;
import org.springframework.data.jpa.domain.Specifications;

import java.util.ArrayList;
import java.util.List;

public class GenericRsqlSpecBuilder<T> {

    public Specifications<T> createSpecification(final Node node) {
        if (node instanceof LogicalNode) {
            return createSpecification((LogicalNode) node);
        }
        if (node instanceof ComparisonNode) {
            return createSpecification((ComparisonNode) node);
        }
        return null;
    }

    public Specifications<T> createSpecification(final LogicalNode logicalNode) {
        final List<Specifications<T>> specs = new ArrayList<Specifications<T>>();
        Specifications<T> temp;
        for (final Node node : logicalNode.getChildren()) {
            temp = createSpecification(node);
            if (temp != null) {
                specs.add(temp);
            }
        }

        Specifications<T> result = specs.get(0);

        if (logicalNode.getOperator() == LogicalOperator.AND) {
            for (int i = 1; i < specs.size(); i++) {
                result = Specifications.where(result).and(specs.get(i));
            }
        }

        else if (logicalNode.getOperator() == LogicalOperator.OR) {
            for (int i = 1; i < specs.size(); i++) {
                result = Specifications.where(result).or(specs.get(i));
            }
        }

        return result;
    }

    public Specifications<T> createSpecification(final ComparisonNode comparisonNode) {
        return Specifications.where(new GenericRsqlSpecification<T>(comparisonNode.getSelector(), comparisonNode.getOperator(), comparisonNode.getArguments()));
    }

}
