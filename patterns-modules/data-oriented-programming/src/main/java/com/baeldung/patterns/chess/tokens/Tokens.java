package com.baeldung.patterns.chess.tokens;

public class Tokens {

	public record KingSideCastle() implements Token {
	}

	public record QueenSideCastle() implements Token {
	}

	public record MoveNumber(int value) implements Token {
	}

	public record PawnMoves(char file, int rank) implements Token {
	}

	public record PawnTakes(char fileFrom, char fileTo, int rankTo) implements Token {
	}

	public record PieceMoves(String piece, char file, int rank) implements Token {
	}

	public record PieceTakes(String piece, char file, int rank) implements Token {
	}
}
