import application.Game;
import domain.Board;
import domain.Player;
import framework.HumanMoveDecider;
import framework.StdOutBoardDisplayer;
import framework.StdOutMessageDevice;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Map<Board.Symbol, Player> mapper = new HashMap<>();

        Player player1 = new Player("Fred", Board.Symbol.CIRCLE, new HumanMoveDecider());
        Player player2 = new Player("Cary", Board.Symbol.CROSS, new HumanMoveDecider());

        mapper.put(Board.Symbol.CIRCLE, player1);
        mapper.put(Board.Symbol.CROSS, player2);

        Game.create(player1, player2)
            .useBoardDisplay(new StdOutBoardDisplayer())
            .useMessageOutDevice(new StdOutMessageDevice() {
                @Override
                public void outWinningSymbol(Board.Symbol symbol) {
                    System.out.println(String.format("Player %s wins!", mapper.get(symbol).getName()));
                }
            })
            .start();
    }
}
