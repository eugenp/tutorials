import java.util.ArrayList;
import java.util.List;

public class FootballTeam {

    private String name;
    private List<Player> players;

    public FootballTeam(String name) {
        this.name = name;
        this.players = new ArrayList<>();
    }

    public void addPlayer(Player player) {
        this.players.add(player);
    }
}
