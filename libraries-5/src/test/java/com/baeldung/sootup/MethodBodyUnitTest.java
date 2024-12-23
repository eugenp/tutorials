package com.baeldung.sootup;

import org.junit.jupiter.api.Test;
import sootup.core.IdentifierFactory;
import sootup.core.graph.StmtGraph;
import sootup.core.inputlocation.AnalysisInputLocation;
import sootup.core.jimple.common.stmt.Stmt;
import sootup.core.model.Body;
import sootup.core.model.SootClass;
import sootup.core.model.SootMethod;
import sootup.core.types.ClassType;
import sootup.java.bytecode.inputlocation.OTFCompileAnalysisInputLocation;
import sootup.java.core.views.JavaView;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MethodBodyUnitTest {
    @Test
    void whenAnalyzingAMethod_thenWeCanAccessTheLocals() {
        Path javaFile = Path.of("src/test/java/com/baeldung/sootup/MethodBodyUnitTest.java");
        AnalysisInputLocation inputLocation = new OTFCompileAnalysisInputLocation(javaFile);

        JavaView view = new JavaView(inputLocation);

        IdentifierFactory identifierFactory = view.getIdentifierFactory();
        ClassType javaClass = identifierFactory.getClassType("com.baeldung.sootup.MethodBodyUnitTest");

        SootClass sootClass = view.getClassOrThrow(javaClass);
        Optional<? extends SootMethod> method = sootClass.getMethod("someMethod",
            List.of(
                identifierFactory.getClassType("java.lang.String")
            ));
        assertTrue(method.isPresent());

        SootMethod sootMethod = method.get();

        Body methodBody = sootMethod.getBody();
        assertThat(methodBody.getLocalCount()).isGreaterThan(0);
        var thisLocal = methodBody.getLocals()
                .stream()
                .filter(local -> local.getName().equals("this"))
                .findFirst();
        assertTrue(thisLocal.isPresent());
        assertEquals(javaClass, thisLocal.get().getType());
    }

    @Test
    void whenAnalyzingAMethod_thenWeCanAccessTheCallGraph() {
        Path javaFile = Path.of("src/test/java/com/baeldung/sootup/MethodBodyUnitTest.java");
        AnalysisInputLocation inputLocation = new OTFCompileAnalysisInputLocation(javaFile);

        JavaView view = new JavaView(inputLocation);

        IdentifierFactory identifierFactory = view.getIdentifierFactory();
        ClassType javaClass = identifierFactory.getClassType("com.baeldung.sootup.MethodBodyUnitTest");

        SootClass sootClass = view.getClassOrThrow(javaClass);
        Optional<? extends SootMethod> method = sootClass.getMethod("someMethod",
            List.of(
                identifierFactory.getClassType("java.lang.String")
            ));
        assertTrue(method.isPresent());

        SootMethod sootMethod = method.get();

        Body methodBody = sootMethod.getBody();
        StmtGraph<?> stmtGraph = methodBody.getStmtGraph();
        List<Stmt> stmts = stmtGraph.getStmts();

        assertThat(stmts).hasSize(7);
        assertThat(stmts.get(0)).asString().isEqualTo("this := @this: com.baeldung.sootup.MethodBodyUnitTest");
        assertThat(stmts.get(1)).asString().isEqualTo("l1 := @parameter0: java.lang.String");
        assertThat(stmts.get(2)).asString().isEqualTo("l2 = virtualinvoke l1.<java.lang.String: java.lang.String toUpperCase()>()");
        assertThat(stmts.get(3)).asString().isEqualTo("$stack3 = <java.lang.System: java.io.PrintStream out>");
        assertThat(stmts.get(4)).asString().isEqualTo("$stack4 = dynamicinvoke \"makeConcatWithConstants\" <java.lang.String (java.lang.String)>(l2) <java.lang.invoke.StringConcatFactory: java.lang.invoke.CallSite makeConcatWithConstants(java.lang.invoke.MethodHandles$Lookup,java.lang.String,java.lang.invoke.MethodType,java.lang.String,java.lang.Object[])>(\"Hello, \\u0001\")");
        assertThat(stmts.get(5)).asString().isEqualTo("virtualinvoke $stack3.<java.io.PrintStream: void println(java.lang.String)>($stack4)");
        assertThat(stmts.get(6)).asString().isEqualTo("return");
    }


    private void someMethod(String name) {
        var capitals = name.toUpperCase();
        System.out.println("Hello, " + capitals);
    }

    private void someMethod(int id) {
        System.out.println("Hello, " + id);
    }
}
