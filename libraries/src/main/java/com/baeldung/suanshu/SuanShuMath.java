package com.baeldung.suanshu;

import java.util.List;

import com.numericalmethod.suanshu.algebra.linear.matrix.doubles.Matrix;
import com.numericalmethod.suanshu.algebra.linear.vector.doubles.Vector;
import com.numericalmethod.suanshu.algebra.linear.vector.doubles.dense.DenseVector;
import com.numericalmethod.suanshu.algebra.linear.matrix.doubles.matrixtype.dense.DenseMatrix;
import com.numericalmethod.suanshu.algebra.linear.matrix.doubles.operation.Inverse;
import com.numericalmethod.suanshu.analysis.function.polynomial.Polynomial;
import com.numericalmethod.suanshu.analysis.function.polynomial.root.PolyRoot;
import com.numericalmethod.suanshu.analysis.function.polynomial.root.PolyRootSolver;
import com.numericalmethod.suanshu.number.complex.Complex;

class SuanShuMath {

    public static void main(String[] args) throws Exception {
        addingVectors();
        scaleVector();
        innerProductVectors();
        addingIncorrectVectors();

        addingMatrices();
        multiplyMatrices();
        multiplyIncorrectMatrices();
        inverseMatrix();

        Polynomial p = createPolynomial();
        evaluatePolynomial(p);
        solvePolynomial();
    }

    public static void addingVectors() throws Exception {
        Vector v1 = new DenseVector(new double[]{1, 2, 3, 4, 5});
        Vector v2 = new DenseVector(new double[]{5, 4, 3, 2, 1});
        Vector v3 = v1.add(v2);
        System.out.println(v3);
    }

    public static void scaleVector() throws Exception {
        Vector v1 = new DenseVector(new double[]{1, 2, 3, 4, 5});
        Vector v2 = v1.scaled(2.0);
        System.out.println(v2);
    }

    public static void innerProductVectors() throws Exception {
        Vector v1 = new DenseVector(new double[]{1, 2, 3, 4, 5});
        Vector v2 = new DenseVector(new double[]{5, 4, 3, 2, 1});
        double inner = v1.innerProduct(v2);
        System.out.println(inner);
    }

    public static void addingIncorrectVectors() throws Exception {
        Vector v1 = new DenseVector(new double[]{1, 2, 3});
        Vector v2 = new DenseVector(new double[]{5, 4});
        Vector v3 = v1.add(v2);
        System.out.println(v3);
    }

    public static void addingMatrices() throws Exception {
        Matrix m1 = new DenseMatrix(new double[][]{
            {1, 2, 3},
            {4, 5, 6}
        });

        Matrix m2 = new DenseMatrix(new double[][]{
            {3, 2, 1},
            {6, 5, 4}
        });

        Matrix m3 = m1.add(m2);
        System.out.println(m3);
    }

    public static void multiplyMatrices() throws Exception {
        Matrix m1 = new DenseMatrix(new double[][]{
            {1, 2, 3},
            {4, 5, 6}
        });

        Matrix m2 = new DenseMatrix(new double[][]{
            {1, 4},
            {2, 5},
            {3, 6}
        });

        Matrix m3 = m1.multiply(m2);
        System.out.println(m3);
    }

    public static void multiplyIncorrectMatrices() throws Exception {
        Matrix m1 = new DenseMatrix(new double[][]{
            {1, 2, 3},
            {4, 5, 6}
        });

        Matrix m2 = new DenseMatrix(new double[][]{
            {3, 2, 1},
            {6, 5, 4}
        });

        Matrix m3 = m1.multiply(m2);
        System.out.println(m3);
    }

    public static void inverseMatrix() {
        Matrix m1 = new DenseMatrix(new double[][]{
            {1, 2},
            {3, 4}
        });

        Inverse m2 = new Inverse(m1);
        System.out.println(m2);
        System.out.println(m1.multiply(m2));
    }

    public static Polynomial createPolynomial() {
        return new Polynomial(new double[]{3, -5, 1});
    }

    public static void evaluatePolynomial(Polynomial p) {
        // Evaluate using a real number
        System.out.println(p.evaluate(5));
        // Evaluate using a complex number
        System.out.println(p.evaluate(new Complex(1, 2)));
    }

    public static void solvePolynomial() {
        Polynomial p = new Polynomial(new double[]{2, 2, -4});
        PolyRootSolver solver = new PolyRoot();
        List<? extends Number> roots = solver.solve(p);
        System.out.println(roots);
    }

}
