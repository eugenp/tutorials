package com.baeldung.commons.math;

public class Launcher {

    public static void main(String[] args) {
        System.out.println("Statistics example:");

        final StatisticsExample statisticsExample = new StatisticsExample();
        statisticsExample.run();

        System.out.println("Probabilities example:");

        final ProbabilitiesExample probabilitiesExample = new ProbabilitiesExample();
        probabilitiesExample.run();

        System.out.println("Root finding example:");

        final RootFindingExample rootFindingExample = new RootFindingExample();
        rootFindingExample.run();

        System.out.println("Integration example:");

        final IntegrationExample integrationExample = new IntegrationExample();
        integrationExample.run();

        System.out.println("Linear algebra example:");

        final LinearAlgebraExample linearAlgebraExample = new LinearAlgebraExample();
        linearAlgebraExample.run();

        System.out.println("Geometry example:");

        final GeometryExample geometryExample = new GeometryExample();
        geometryExample.run();

        System.out.println("Fraction and complex example:");

        final FractionAndComplexExample fractionAndComplexExample = new FractionAndComplexExample();
        fractionAndComplexExample.run();
    }

}
