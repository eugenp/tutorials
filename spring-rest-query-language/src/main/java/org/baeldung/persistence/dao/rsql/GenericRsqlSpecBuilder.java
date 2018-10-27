package org.baeldung.persistence.dao.rsql;

import java.util.ArrayList;
import java.util.List;
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
                .filter(specifications -> specifications != null)
                .collect(Collectors.toCollection(ArrayList::new));
        
        Specifications<T> initialSpec = specs.stream().findFirst().get();
        
        Specifications<T> result = specs.stream().skip(1).reduce(initialSpec, (firstSpec, secondSpec) -> {
            if (logicalNode.getOperator() == LogicalOperator.AND) {
                return Specifications.where(firstSpec).and(secondSpec);
            } else if (logicalNode.getOperator() == LogicalOperator.OR) {
                return Specifications.where(firstSpec).or(secondSpec);    
            }
            return firstSpec;
        });
        
        return result;
    }

    public Specifications<T> createSpecification(final ComparisonNode comparisonNode) {
        return Specifications.where(new GenericRsqlSpecification<T>(comparisonNode.getSelector(), comparisonNode.getOperator(), comparisonNode.getArguments()));
    }

}
