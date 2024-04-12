package com.baeldung.o;

public class Division implements CalculatorOperation {
	private double left;
	private double right;
	private double result = 0.0;

	public Division(double left, double right) {
		this.left = left;
		this.right = right;
	}

	public double getLeft() {
		return left;
	}

	public void setLeft(double left) {
		this.left = left;
	}

	public double getRight() {
		return right;
	}

	public void setRight(double right) {
		this.right = right;
	}

	public double getResult() {
		return result;
	}

	public void setResult(double result) {
		this.result = result;
	}

	@Override
	public void perform() {
		if (right != 0) {
			result = left / right;
		}
	}
}
