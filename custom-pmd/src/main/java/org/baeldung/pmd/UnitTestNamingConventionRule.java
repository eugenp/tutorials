package org.baeldung.pmd;

import java.util.Arrays;
import java.util.List;

import net.sourceforge.pmd.lang.java.ast.ASTClassOrInterfaceDeclaration;
import net.sourceforge.pmd.lang.java.rule.AbstractJavaRule;

public class UnitTestNamingConventionRule extends AbstractJavaRule {

	private static List<String> allowedEndings  = Arrays.asList("IntegrationTest", "LongRunningUnitTest", "ManualTest",
			"JdbcTest", "LiveTest");
	
	public Object visit(ASTClassOrInterfaceDeclaration node, Object data) {
		
		String className = node.getImage();
		if (className != null && className.endsWith("Test")) {
			System.out.println("Applying UnitTestNamingConventionRule to test - " + className);
			
			boolean followsNamingConvention = allowedEndings.stream().anyMatch(ending -> className.endsWith(ending));
			if(!followsNamingConvention) {
				addViolation(data, node);
			}
		}
		
		return data;
	}
}