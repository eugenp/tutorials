package com.baeldung.extractjava;

import com.sun.source.tree.ClassTree;
import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.Tree;
import com.sun.source.util.JavacTask;
import com.sun.source.util.SimpleTreeVisitor;
import org.junit.Test;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class ExtractJavaLiveTest {
    @Test
    public void visitTypeDeclsForClasses() throws Exception {
        CompilationUnitTree compilationUnitTree = parseFile();

        for (Tree tree : compilationUnitTree.getTypeDecls()) {
            tree.accept(new SimpleTreeVisitor() {
                @Override
                public Object visitClass(ClassTree classTree, Object o) {
                    System.out.println("Found class: " + classTree.getSimpleName());
                    return null;
                }
            }, null);
        }
    }

    @Test
    public void iterateTypeDeclsForClasses() throws Exception {
        CompilationUnitTree compilationUnitTree = parseFile();

        for (Tree tree : compilationUnitTree.getTypeDecls()) {
            if (tree.getKind() == Tree.Kind.CLASS) {
                ClassTree classTree = (ClassTree) tree;
                System.out.println("Found class: " + classTree.getSimpleName());
            }
        }
    }

    @Test
    public void visitMethodsForClass() throws Exception {
        CompilationUnitTree compilationUnitTree = parseFile();

        for (Tree tree : compilationUnitTree.getTypeDecls()) {
            if (tree.getKind() == Tree.Kind.CLASS) {
                ClassTree classTree = (ClassTree) tree;
                visitClassMethods(classTree);
            }
        }

    }

    private void visitClassMethods(ClassTree classTree) {
        for (Tree member : classTree.getMembers()) {
            member.accept(new SimpleTreeVisitor(){
                @Override
                public Object visitMethod(MethodTree methodTree, Object o) {
                    System.out.println("Found method: " + classTree.getSimpleName() + "." + methodTree.getName());
                    System.out.println("Return value: " + methodTree.getReturnType());
                    System.out.println("Parameters: " + methodTree.getParameters());

                    for (Tree statement : methodTree.getBody().getStatements()) {
                        System.out.println("Found statement: " + statement);
                    }

                    return null;
                }
            }, null);
        }
    }

    private static CompilationUnitTree parseFile() throws IOException {
        String filename = "src/test/java/com/baeldung/extractjava/ExtractJavaUnitTest.java";

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, StandardCharsets.UTF_8);
        Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromFiles(Arrays.asList(new File(filename)));

        JavacTask javacTask = (JavacTask) compiler.getTask(null, fileManager, null, null, null, compilationUnits);
        Iterable<? extends CompilationUnitTree> compilationUnitTrees = javacTask.parse();

        CompilationUnitTree compilationUnitTree = compilationUnitTrees.iterator().next();
        return compilationUnitTree;
    }
}
