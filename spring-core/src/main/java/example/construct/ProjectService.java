package example.construct;

/**
 * Created by Gaurav on 22/08/17.
 */
public class ProjectService {
    private ProjectDao projectDao;


    public ProjectService(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }
}
