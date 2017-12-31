package org.baeldung.persistence.dao;

import com.querydsl.core.types.dsl.StringExpression;
import org.baeldung.persistence.model.MyUser;
import org.baeldung.persistence.model.QMyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.querydsl.binding.SingleValueBinding;

public interface MyUserRepository extends JpaRepository<MyUser, Long>, QueryDslPredicateExecutor<MyUser>, QuerydslBinderCustomizer<QMyUser> {
    @Override
    default public void customize(final QuerydslBindings bindings, final QMyUser root) {
        bindings.bind(String.class)
          .first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
        bindings.excluding(root.email);
    }

}
