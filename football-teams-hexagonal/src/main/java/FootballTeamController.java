public class FootballTeamController {

    private FootballTeamService footballTeamService;

    public FootballTeam create(FootballTeam footballTeam) {
        return footballTeamService.save(footballTeam);
    }

    public void addPlayer(Long footballTeamId, Player player) {
        footballTeamService.addPlayer(footballTeamId, player);
    }
}
