package com.baeldung.cloning.deep;

public class Engine implements Cloneable {
	private String chessisNumber;

	public Engine(String chessisNumber) {
		this.chessisNumber = chessisNumber;
	}

	public String getChessisNumber() {
		return chessisNumber;
	}

	public void setChessisNumber(String chessisNumber) {
		this.chessisNumber = chessisNumber;
	}

	@Override
	public Engine clone() {
		try {
			Engine clonedEngine = (Engine) super.clone();
			return clonedEngine;
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}
}