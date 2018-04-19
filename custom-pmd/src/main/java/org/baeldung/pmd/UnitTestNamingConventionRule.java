package org.baeldung.pmd;

import net.sourceforge.pmd.lang.java.ast.ASTClassOrInterfaceDeclaration;
import net.sourceforge.pmd.lang.java.rule.AbstractJavaRule;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class UnitTestNamingConventionRule extends AbstractJavaRule {

    private static List<String> allowedEndings = Arrays.asList(
      "IntegrationTest",
      "ManualTest",
      "JdbcTest",
      "LiveTest",
      "UnitTest");

    public Object visit(ASTClassOrInterfaceDeclaration node, Object data) {
        String className = node.getImage();
        Objects.requireNonNull(className);

        if (className.endsWith("Test") || className.endsWith("Tests")) {
            if (allowedEndings.stream()
              .noneMatch(className::endsWith)) {
                addViolation(data, node);
            }
        }

        return data;
    }
}
