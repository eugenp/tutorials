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
        assertTrue(methodBody.getLocalCount() > 0);
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
        for (Stmt stmt : stmts) {
            System.out.println(stmt);
        }
    }


    private void someMethod(String name) {
        var capitald = name.toUpperCase();
        System.out.println("Hello, " + capitald);
    }
}
