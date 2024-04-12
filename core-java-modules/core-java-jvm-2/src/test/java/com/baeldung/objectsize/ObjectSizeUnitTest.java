package com.baeldung.objectsize;

import org.junit.Test;
import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.info.GraphLayout;
import org.openjdk.jol.vm.VM;

public class ObjectSizeUnitTest {

    @Test
    public void printingTheVMDetails() {
        System.out.println(VM.current().details());
    }

    @Test
    public void printingTheProfClassLayout() {
        System.out.println(ClassLayout.parseClass(Professor.class).toPrintable());
    }

    @Test
    public void printingTheCourseClassLayout() {
        System.out.println(ClassLayout.parseClass(Course.class).toPrintable());
    }

    @Test
    public void printingACourseInstanceLayout() {
        String ds = "Data Structures";
        Course course = new Course(ds);

        System.out.println("The shallow size is :" + VM.current().sizeOf(course));

        System.out.println(ClassLayout.parseInstance(course).toPrintable());
        System.out.println(ClassLayout.parseInstance(ds).toPrintable());
        System.out.println(ClassLayout.parseInstance(ds.toCharArray()).toPrintable());

        System.out.println(GraphLayout.parseInstance(course).totalSize());
        System.out.println(GraphLayout.parseInstance(course).toFootprint());
        System.out.println(GraphLayout.parseInstance(course).toPrintable());
    }
}
