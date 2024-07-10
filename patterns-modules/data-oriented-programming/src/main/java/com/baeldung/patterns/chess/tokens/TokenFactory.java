package com.baeldung.patterns.chess.tokens;

import com.baeldung.patterns.chess.tokens.Tokens.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TokenFactory {
	static Pattern moveNumberPattern = Pattern.compile("(\\d+)\\.");
	static Pattern pawnMovesPattern = Pattern.compile("^([a-h])([1-8])$");
	static Pattern pawnTakesPattern = Pattern.compile("^([a-h])x([a-h])([1-8])$");
	static Pattern pieceMovesPattern = Pattern.compile("^([BNRQK])([a-h])([1-8])$");
	static Pattern pieceTakesPattern = Pattern.compile("^([BNRQK])x([a-h])([1-8])$");
	static Pattern kingSideCastlingPattern = Pattern.compile("^O-O$");
	static Pattern queenSideCastlingPattern = Pattern.compile("^O-O-O$");

	public static Token fromString(String rawString) {
		Matcher moveNo = moveNumberPattern.matcher(rawString);
		if (moveNo.matches())
			return new MoveNumber(Integer.parseInt(moveNo.group(1)));

		var pawnMove = pawnMovesPattern.matcher(rawString);
		if (pawnMove.matches()) {
			char file = pawnMove.group(1).charAt(0);
			int rank = Integer.parseInt(pawnMove.group(2));
			return new Tokens.PawnMoves(file, rank);
		}

		var pawnTakes = pawnTakesPattern.matcher(rawString);
		if (pawnTakes.matches()) {
			char fileFrom = pawnTakes.group(1).charAt(0);
			char fileTo = pawnTakes.group(2).charAt(0);
			int rankTo = Integer.parseInt(pawnTakes.group(3));
			return new PawnTakes(fileFrom, fileTo, rankTo);
		}

		var pieceMoves = pieceMovesPattern.matcher(rawString);
		if (pieceMoves.matches()) {
			String piece = pieceName(pieceMoves.group(1).charAt(0));
			char file = pieceMoves.group(2).charAt(0);
			int rank = Integer.parseInt(pieceMoves.group(3));
			return new PieceMoves(piece, file, rank);
		}

		var pieceTakes = pieceTakesPattern.matcher(rawString);
		if (pieceTakes.matches()) {
			String piece = pieceName(pieceTakes.group(1).charAt(0));
			char file = pieceTakes.group(2).charAt(0);
			int rank = Integer.parseInt(pieceTakes.group(3));
			return new PieceTakes(piece, file, rank);
		}

		if (kingSideCastlingPattern.matcher(rawString).matches())
			return new KingSideCastle();

		if (queenSideCastlingPattern.matcher(rawString).matches())
			return new QueenSideCastle();

		throw new IllegalArgumentException(rawString + " is not a known token!");
	}

	static String pieceName(char abbreviation) {
		return switch (abbreviation) {
			case 'N' -> "Knight";
			case 'B' -> "Bishop";
			case 'R' -> "Rook";
			case 'Q' -> "Queen";
			case 'K' -> "King";
			default -> throw new IllegalArgumentException();
		};
	}
}
