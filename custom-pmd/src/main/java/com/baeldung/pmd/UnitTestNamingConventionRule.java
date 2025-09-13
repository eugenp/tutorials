package com.baeldung.pmd;

import net.sourceforge.pmd.lang.java.ast.ASTClassDeclaration;
import net.sourceforge.pmd.lang.java.ast.JavaVisitorBase;
import net.sourceforge.pmd.reporting.RuleContext;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class UnitTestNamingConventionRule extends JavaVisitorBase<RuleContext, Void>{

    private static List<String> allowedEndings = Arrays.asList(
      "IntegrationTest",
      "IntTest",
      "ManualTest",
      "JdbcTest",
      "LiveTest",
      "UnitTest",
      "jmhTest");

    @Override
    public Void visit(ASTClassDeclaration node, RuleContext ctx) {
        String className = node.getSimpleName();
        Objects.requireNonNull(className);

        if (className.endsWith("SpringContextTest")) {
            return null;
        }

        if (className.endsWith("Tests")
            || (className.endsWith("Test") && allowedEndings.stream().noneMatch(className::endsWith))) {
            ctx.addViolation(node);
        }

        return null;
    }
}
