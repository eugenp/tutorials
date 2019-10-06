package com.baeldung.aggregation;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.aggregation.model.custom.ICommentCount;
import com.baeldung.aggregation.model.custom.CommentCount;
import com.baeldung.aggregation.repository.CommentRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@Sql(scripts = "/test-aggregation-data.sql")
public class SpringDataAggregateIntegrationTest {

	@Autowired
	private CommentRepository commentRepository;

	@Test
	public void whenQueryWithAggregation_thenReturnCustomResult() {
		List<CommentCount> commentsYear = commentRepository.countTotalCommentsByYearClass();
		CommentCount countYear2019 = commentsYear.get(0);
		Assert.assertTrue(countYear2019.getYear().equals(2019));
		Assert.assertEquals(countYear2019.getTotal().longValue(), 1l);
		CommentCount countYear2018 = commentsYear.get(1);
		Assert.assertTrue(countYear2018.getYear().equals(2018));
		Assert.assertEquals(countYear2018.getTotal().longValue(), 2l);
		CommentCount countYear2017 = commentsYear.get(2);
		Assert.assertTrue(countYear2017.getYear().equals(2017));
		Assert.assertEquals(countYear2017.getTotal().longValue(), 1l);
	}

	@Test
	public void whenQueryWithAggregation_thenReturnInterfaceResult() {
		List<ICommentCount> commentsYear = commentRepository.countTotalCommentsByYearInterface();
        ICommentCount countYear2019 = commentsYear.get(0);
        Assert.assertTrue(countYear2019.getYearComment().equals(2019));
        Assert.assertEquals(countYear2019.getTotalComment().longValue(), 1l);
        ICommentCount countYear2018 = commentsYear.get(1);
        Assert.assertTrue(countYear2018.getYearComment().equals(2018));
        Assert.assertEquals(countYear2018.getTotalComment().longValue(), 2l);
        ICommentCount countYear2017 = commentsYear.get(2);
        Assert.assertTrue(countYear2017.getYearComment().equals(2017));
        Assert.assertEquals(countYear2017.getTotalComment().longValue(), 1l);
	}

	@Test
	public void whenNativeQueryWithAggregation_thenReturnInterfaceResult() {
        List<ICommentCount> commentsYear = commentRepository.countTotalCommentsByYearNative();
        ICommentCount countYear2019 = commentsYear.get(0);
        Assert.assertTrue(countYear2019.getYearComment().equals(2019));
        Assert.assertEquals(countYear2019.getTotalComment().longValue(), 1l);
        ICommentCount countYear2018 = commentsYear.get(1);
        Assert.assertTrue(countYear2018.getYearComment().equals(2018));
        Assert.assertEquals(countYear2018.getTotalComment().longValue(), 2l);
        ICommentCount countYear2017 = commentsYear.get(2);
        Assert.assertTrue(countYear2017.getYearComment().equals(2017));
        Assert.assertEquals(countYear2017.getTotalComment().longValue(), 1l);
	}

}
