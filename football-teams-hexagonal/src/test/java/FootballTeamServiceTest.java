import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class FootballTeamServiceTest {

    private FootballTeamRepository footballTeamRepository;
    private FootballTeamService footballTeamService;

    @BeforeEach
    void setUp() {
        footballTeamRepository = mock(FootballTeamRepository.class);
        footballTeamService = new FootballTeamService(footballTeamRepository);
    }

    @Test
    void saveFootballTeam() {
        FootballTeam footballTeam = new FootballTeam("Rayo Vallecano");
        footballTeamService.save(footballTeam);
        verify(footballTeamRepository).save(any(FootballTeam.class));
    }
}
