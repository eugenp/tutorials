package com.baeldung.cloning.shallow;

public class Engine {
	
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
}