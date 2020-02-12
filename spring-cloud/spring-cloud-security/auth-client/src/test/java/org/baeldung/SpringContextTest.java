package org.baeldung;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.CloudSite;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CloudSite.class)
public class SpringContextTest {

	@Test
	public void contextLoads() {
	}

}
