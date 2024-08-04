package com.baeldung.dddjmolecules;

import org.jmolecules.archunit.JMoleculesDddRules;

import com.baeldung.dddjmolecules.article.Article;
import com.baeldung.dddjmolecules.author.Author;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;

@AnalyzeClasses(packagesOf = { Article.class, Author.class })
class JMoleculesDddUnitTest {

    /*
        - Aggregates only refer to entities that are declared to be part of it.
        References to other aggregates are established via org.jmolecules.ddd.types.Associations or identifier references.
        Annotated identifiables do have an identifier.
        Value objects and identifiers do not refer to identifiables.
    * */

    @ArchTest
    void verifyModel(JavaClasses classes) {
        JMoleculesDddRules.all()
            .check(classes);
    }

}

