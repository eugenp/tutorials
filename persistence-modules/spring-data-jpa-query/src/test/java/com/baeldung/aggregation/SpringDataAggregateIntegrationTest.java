package com.baeldung.aggregation;

import com.baeldung.aggregation.model.custom.CommentCount;
import com.baeldung.aggregation.model.custom.ICommentCount;
import com.baeldung.aggregation.repository.CommentRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest(showSql = false)
@Sql(scripts = "/test-aggregation-data.sql")
public class SpringDataAggregateIntegrationTest {

    @Autowired
    private CommentRepository commentRepository;

    @Test
    public void whenQueryWithAggregation_thenReturnResult() {
        List<Object[]> commentCountsByYear = commentRepository.countTotalCommentsByYear();

        Object[] countYear2019 = commentCountsByYear.get(0);

        assertThat(countYear2019[0], is(Integer.valueOf(2019)));
        assertThat(countYear2019[1], is(1l));

        Object[] countYear2018 = commentCountsByYear.get(1);

        assertThat(countYear2018[0], is(Integer.valueOf(2018)));
        assertThat(countYear2018[1], is(2l));

        Object[] countYear2017 = commentCountsByYear.get(2);

        assertThat(countYear2017[0], is(Integer.valueOf(2017)));
        assertThat(countYear2017[1], is(1l));
    }

    @Test
    public void whenQueryWithAggregation_thenReturnCustomResult() {
        List<CommentCount> commentCountsByYear = commentRepository.countTotalCommentsByYearClass();

        CommentCount countYear2019 = commentCountsByYear.get(0);

        assertThat(countYear2019.getYear(), is(Integer.valueOf(2019)));
        assertThat(countYear2019.getTotal(), is(1l));

        CommentCount countYear2018 = commentCountsByYear.get(1);

        assertThat(countYear2018.getYear(), is(Integer.valueOf(2018)));
        assertThat(countYear2018.getTotal(), is(2l));

        CommentCount countYear2017 = commentCountsByYear.get(2);

        assertThat(countYear2017.getYear(), is(Integer.valueOf(2017)));
        assertThat(countYear2017.getTotal(), is(1l));
    }

    @Test
    public void whenQueryWithAggregation_thenReturnInterfaceResult() {
        List<ICommentCount> commentCountsByYear = commentRepository.countTotalCommentsByYearInterface();

        ICommentCount countYear2019 = commentCountsByYear.get(0);

        assertThat(countYear2019.getYearComment(), is(Integer.valueOf(2019)));
        assertThat(countYear2019.getTotalComment(), is(1l));

        ICommentCount countYear2018 = commentCountsByYear.get(1);

        assertThat(countYear2018.getYearComment(), is(Integer.valueOf(2018)));
        assertThat(countYear2018.getTotalComment(), is(2l));

        ICommentCount countYear2017 = commentCountsByYear.get(2);

        assertThat(countYear2017.getYearComment(), is(Integer.valueOf(2017)));
        assertThat(countYear2017.getTotalComment(), is(1l));
    }

    @Test
    public void whenNativeQueryWithAggregation_thenReturnInterfaceResult() {
        List<ICommentCount> commentCountsByYear = commentRepository.countTotalCommentsByYearNative();

        ICommentCount countYear2019 = commentCountsByYear.get(0);

        assertThat(countYear2019.getYearComment(), is(Integer.valueOf(2019)));
        assertThat(countYear2019.getTotalComment(), is(1l));

        ICommentCount countYear2018 = commentCountsByYear.get(1);

        assertThat(countYear2018.getYearComment(), is(Integer.valueOf(2018)));
        assertThat(countYear2018.getTotalComment(), is(2l));

        ICommentCount countYear2017 = commentCountsByYear.get(2);

        assertThat(countYear2017.getYearComment(), is(Integer.valueOf(2017)));
        assertThat(countYear2017.getTotalComment(), is(1l));
    }

}
