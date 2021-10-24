public class FootballTeamService {

    private FootballTeamRepository footballTeamRepository;

    public FootballTeamService(FootballTeamRepository footballTeamRepository) {
        this.footballTeamRepository = footballTeamRepository;
    }

    public FootballTeam save(FootballTeam footballTeam) {
        return footballTeamRepository.save(footballTeam);
    }

    public void addPlayer(Long footballTeamId, Player player) {
        FootballTeam footballTeam = footballTeamRepository.findById(footballTeamId);
        footballTeam.addPlayer(player);
        footballTeamRepository.save(footballTeam);
    }
}
