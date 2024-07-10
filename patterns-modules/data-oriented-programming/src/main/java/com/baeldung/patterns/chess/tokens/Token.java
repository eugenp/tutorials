package com.baeldung.patterns.chess.tokens;

import com.baeldung.patterns.chess.tokens.Tokens.*;

public sealed interface Token
		permits MoveNumber, PawnMoves, PawnTakes, PieceMoves, PieceTakes, QueenSideCastle, KingSideCastle {
}
