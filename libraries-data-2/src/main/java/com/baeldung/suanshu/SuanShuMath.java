package com.baeldung.suanshu;

/*
import com.numericalmethod.suanshu.algebra.linear.matrix.doubles.Matrix;
import com.numericalmethod.suanshu.algebra.linear.matrix.doubles.matrixtype.dense.DenseMatrix;
import com.numericalmethod.suanshu.algebra.linear.matrix.doubles.operation.Inverse;
import com.numericalmethod.suanshu.algebra.linear.vector.doubles.Vector;
import com.numericalmethod.suanshu.algebra.linear.vector.doubles.dense.DenseVector;
import com.numericalmethod.suanshu.analysis.function.polynomial.Polynomial;
import com.numericalmethod.suanshu.analysis.function.polynomial.root.PolyRoot;
import com.numericalmethod.suanshu.analysis.function.polynomial.root.PolyRootSolver;
import com.numericalmethod.suanshu.number.complex.Complex;
*/
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

class SuanShuMath {
/** fixing in JAVA-24004
    private static final Logger log = LoggerFactory.getLogger(SuanShuMath.class);

    public static void main(String[] args) throws Exception {
        SuanShuMath math = new SuanShuMath();

        math.addingVectors();
        math.scaleVector();
        math.innerProductVectors();
        math.addingIncorrectVectors();

        math.addingMatrices();
        math.multiplyMatrices();
        math.multiplyIncorrectMatrices();
        math.inverseMatrix();

        Polynomial p = math.createPolynomial();
        math.evaluatePolynomial(p);
        math.solvePolynomial();
    }

    public void addingVectors() throws Exception {
        Vector v1 = new DenseVector(new double[]{1, 2, 3, 4, 5});
        Vector v2 = new DenseVector(new double[]{5, 4, 3, 2, 1});
        Vector v3 = v1.add(v2);
        log.info("Adding vectors: {}", v3);
    }

    public void scaleVector() throws Exception {
        Vector v1 = new DenseVector(new double[]{1, 2, 3, 4, 5});
        Vector v2 = v1.scaled(2.0);
        log.info("Scaling a vector: {}", v2);
    }

    public void innerProductVectors() throws Exception {
        Vector v1 = new DenseVector(new double[]{1, 2, 3, 4, 5});
        Vector v2 = new DenseVector(new double[]{5, 4, 3, 2, 1});
        double inner = v1.innerProduct(v2);
        log.info("Vector inner product: {}", inner);
    }

    public void addingIncorrectVectors() throws Exception {
        Vector v1 = new DenseVector(new double[]{1, 2, 3});
        Vector v2 = new DenseVector(new double[]{5, 4});
        Vector v3 = v1.add(v2);
        log.info("Adding vectors: {}", v3);
    }

    public void addingMatrices() throws Exception {
        Matrix m1 = new DenseMatrix(new double[][]{
            {1, 2, 3},
            {4, 5, 6}
        });

        Matrix m2 = new DenseMatrix(new double[][]{
            {3, 2, 1},
            {6, 5, 4}
        });

        Matrix m3 = m1.add(m2);
        log.info("Adding matrices: {}", m3);
    }

    public void multiplyMatrices() throws Exception {
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
        log.info("Multiplying matrices: {}", m3);
    }

    public void multiplyIncorrectMatrices() throws Exception {
        Matrix m1 = new DenseMatrix(new double[][]{
            {1, 2, 3},
            {4, 5, 6}
        });

        Matrix m2 = new DenseMatrix(new double[][]{
            {3, 2, 1},
            {6, 5, 4}
        });

        Matrix m3 = m1.multiply(m2);
        log.info("Multiplying matrices: {}", m3);
    }

    public void inverseMatrix() {
        Matrix m1 = new DenseMatrix(new double[][]{
            {1, 2},
            {3, 4}
        });

        Inverse m2 = new Inverse(m1);
        log.info("Inverting a matrix: {}", m2);
        log.info("Verifying a matrix inverse: {}", m1.multiply(m2));
    }

    public Polynomial createPolynomial() {
        return new Polynomial(new double[]{3, -5, 1});
    }

    public void evaluatePolynomial(Polynomial p) {
        // Evaluate using a real number
        log.info("Evaluating a polynomial using a real number: {}", p.evaluate(5));
        // Evaluate using a complex number
        log.info("Evaluating a polynomial using a complex number: {}", p.evaluate(new Complex(1, 2)));
    }

    public void solvePolynomial() {
        Polynomial p = new Polynomial(new double[]{2, 2, -4});
        PolyRootSolver solver = new PolyRoot();
        List<? extends Number> roots = solver.solve(p);
        log.info("Finding polynomial roots: {}", roots);
    }
*/
}
