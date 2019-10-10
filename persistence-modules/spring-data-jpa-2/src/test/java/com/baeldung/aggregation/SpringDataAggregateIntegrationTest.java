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

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
@Sql(scripts = "/test-aggregation-data.sql")
public class SpringDataAggregateIntegrationTest {

    @Autowired
    private CommentRepository commentRepository;

    @Test
    public void whenQueryWithAggregation_thenReturnCustomResult() {
        List<CommentCount> commentCountsByYear = commentRepository.countTotalCommentsByYearClass();
        CommentCount countYear2019 = commentCountsByYear.get(0);
        assertEquals(countYear2019.getYear(), Integer.valueOf(2019));
        assertEquals(countYear2019.getTotal().longValue(), 1l);
        CommentCount countYear2018 = commentCountsByYear.get(1);
        assertEquals(countYear2018.getYear(), Integer.valueOf(2018));
        assertEquals(countYear2018.getTotal().longValue(), 2l);
        CommentCount countYear2017 = commentCountsByYear.get(2);
        assertEquals(countYear2017.getYear(), Integer.valueOf(2017));
        assertEquals(countYear2017.getTotal().longValue(), 1l);
    }

    @Test
    public void whenQueryWithAggregation_thenReturnInterfaceResult() {
        List<ICommentCount> commentCountsByYear = commentRepository.countTotalCommentsByYearInterface();
        ICommentCount countYear2019 = commentCountsByYear.get(0);
        assertEquals(countYear2019.getYearComment(), Integer.valueOf(2019));
        assertEquals(countYear2019.getTotalComment().longValue(), 1l);
        ICommentCount countYear2018 = commentCountsByYear.get(1);
        assertEquals(countYear2018.getYearComment(), Integer.valueOf(2018));
        assertEquals(countYear2018.getTotalComment().longValue(), 2l);
        ICommentCount countYear2017 = commentCountsByYear.get(2);
        assertEquals(countYear2017.getYearComment(), Integer.valueOf(2017));
        assertEquals(countYear2017.getTotalComment().longValue(), 1l);
    }

    @Test
    public void whenNativeQueryWithAggregation_thenReturnInterfaceResult() {
        List<ICommentCount> commentCountsByYear = commentRepository.countTotalCommentsByYearNative();
        ICommentCount countYear2019 = commentCountsByYear.get(0);
        assertEquals(countYear2019.getYearComment(), Integer.valueOf(2019));
        assertEquals(countYear2019.getTotalComment().longValue(), 1l);
        ICommentCount countYear2018 = commentCountsByYear.get(1);
        assertEquals(countYear2018.getYearComment(), Integer.valueOf(2018));
        assertEquals(countYear2018.getTotalComment().longValue(), 2l);
        ICommentCount countYear2017 = commentCountsByYear.get(2);
        assertEquals(countYear2017.getYearComment(), Integer.valueOf(2017));
        assertEquals(countYear2017.getTotalComment().longValue(), 1l);
    }

}
