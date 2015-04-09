package org.baeldung.persistence.dao.rsql;

import java.util.ArrayList;
import java.util.List;

import org.baeldung.persistence.model.User;
import org.springframework.data.jpa.domain.Specifications;

import cz.jirutka.rsql.parser.ast.ComparisonNode;
import cz.jirutka.rsql.parser.ast.LogicalNode;
import cz.jirutka.rsql.parser.ast.LogicalOperator;
import cz.jirutka.rsql.parser.ast.Node;

public class UserRsqlSpecBuilder {

    public Specifications<User> createSpecification(final Node node) {
        if (node instanceof LogicalNode) {
            return createSpecification((LogicalNode) node);
        }
        if (node instanceof ComparisonNode) {
            return createSpecification((ComparisonNode) node);
        }
        return null;
    }

    public Specifications<User> createSpecification(final LogicalNode logicalNode) {
        final List<Specifications<User>> specs = new ArrayList<Specifications<User>>();
        Specifications<User> temp;
        for (final Node node : logicalNode.getChildren()) {
            temp = createSpecification(node);
            if (temp != null) {
                specs.add(temp);
            }
        }

        Specifications<User> result = specs.get(0);

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

    public Specifications<User> createSpecification(final ComparisonNode comparisonNode) {
        final Specifications<User> result = Specifications.where(new UserRsqlSpecification(comparisonNode.getSelector(), comparisonNode.getOperator(), comparisonNode.getArguments()));
        return result;
    }

}
