package org.baeldung.persistence.dao.rsql;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.data.jpa.domain.Specifications;

import cz.jirutka.rsql.parser.ast.ComparisonNode;
import cz.jirutka.rsql.parser.ast.LogicalNode;
import cz.jirutka.rsql.parser.ast.LogicalOperator;
import cz.jirutka.rsql.parser.ast.Node;

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
        
        List<Specifications<T>> specs = logicalNode.getChildren()
                .stream()
                .map(node -> createSpecification(node))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        
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
