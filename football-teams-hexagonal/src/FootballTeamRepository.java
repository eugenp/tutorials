public interface FootballTeamRepository {

    FootballTeam save(FootballTeam footballTeam);

    FootballTeam findById(Long id);
}
