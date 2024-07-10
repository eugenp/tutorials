package com.baeldung.patterns.chess;

import com.baeldung.patterns.chess.tokens.Token;
import com.baeldung.patterns.chess.tokens.TokenFactory;
import com.baeldung.patterns.chess.tokens.Tokens.*;

import java.util.List;
import java.util.stream.Stream;

public class ChessGamePlayback {

	public static Stream<String> playback(String chessGameInSanFormat) {
		List<Token> tokens = Stream.of(chessGameInSanFormat.split("\\s+"))
				.map(TokenFactory::fromString)
				.toList();

		return tokens.stream()
				.map(ChessGamePlayback::process);
	}

	private static String process(Token it) {
		return switch (it) {
			case MoveNumber(int no) -> "#" + no;
			case PawnMoves(char file, int rank) -> "Pawn to '%s%s'".formatted(file, rank);
			case PawnTakes(char fileFrom, char fileTo, int rankTo) ->
					"%s Pawn takes on '%s%s'".formatted(fileFrom, fileTo, rankTo);
			case PieceMoves(String name, char file, int rank) -> "%s to '%s%s'".formatted(name, file, rank);
			case PieceTakes(String piece, char file, int rank) -> "%s takes on '%s%s'".formatted(piece, file, rank);
			case KingSideCastle __ -> "Castling King side";
			case QueenSideCastle __ -> "Castling Queen side";
		};
	}
}
