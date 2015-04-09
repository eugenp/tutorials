package org.baeldung.persistence.dao.rsql;

import org.baeldung.persistence.model.User;
import org.springframework.data.jpa.domain.Specification;

import cz.jirutka.rsql.parser.ast.AndNode;
import cz.jirutka.rsql.parser.ast.ComparisonNode;
import cz.jirutka.rsql.parser.ast.OrNode;
import cz.jirutka.rsql.parser.ast.RSQLVisitor;

public class CustomRsqlVisitor<T> implements RSQLVisitor<Specification<User>, Void> {

    private UserRsqlSpecBuilder builder;

    public CustomRsqlVisitor() {
        builder = new UserRsqlSpecBuilder();
    }

    @Override
    public Specification<User> visit(final AndNode node, final Void param) {
        return builder.createSpecification(node);
    }

    @Override
    public Specification<User> visit(final OrNode node, final Void param) {
        return builder.createSpecification(node);
    }

    @Override
    public Specification<User> visit(final ComparisonNode node, final Void params) {
        return builder.createSpecification(node);
    }

}
