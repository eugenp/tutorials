package example.setter;

/**
 * Created by Gaurav on 22/08/17.
 */
public class ProjectService {

    public  ProjectService(){}

    private ProjectDao projectDao;


    public void setProjectDao(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }
}
