import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;


import com.tut.questionnaire.QuestionService;
import com.tut.questionnaire.QuestionnaireController;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {com.tut.questionnaire.config.QuesConfiguration.class})
public class AppTest {

	//DI
    @Autowired
    @Qualifier("questionController")
    QuestionnaireController qCtrller;

    @Test
    public void TestDependencyInjection() {

        //assert correct type/impl
        assertThat(qCtrller.getQuestionService(), instanceOf(QuestionService.class));

        //assert true
        assertThat(qCtrller.getQuestionService().getQuestion(), is(true));

    }
}