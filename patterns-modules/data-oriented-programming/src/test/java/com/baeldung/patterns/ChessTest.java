package com.baeldung.patterns;

import com.baeldung.patterns.chess.ChessGamePlayback;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class ChessTest {

	@Test
	void chessGame() {
		String rawText = """
				1. e4 e6  
				2. b3 d5  
				3. Bb2 dxe4  
				4. Nc3 Nf6  
				5. Qe2 Bb4  
				6. O-O-O Qe7  
				""";

		Stream<String> moves = ChessGamePlayback.playback(rawText);

		assertThat(moves).containsExactly(
				"#1",
				"Pawn to 'e4'",
				"Pawn to 'e6'",
				"#2",
				"Pawn to 'b3'",
				"Pawn to 'd5'",
				"#3",
				"Bishop to 'b2'",
				"d Pawn takes on 'e4'",
				"#4",
				"Knight to 'c3'",
				"Knight to 'f6'",
				"#5",
				"Queen to 'e2'",
				"Bishop to 'b4'",
				"#6",
				"Castling Queen side",
				"Queen to 'e7'"
		);

	}

}
