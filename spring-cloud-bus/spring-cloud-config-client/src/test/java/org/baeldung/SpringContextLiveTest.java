package org.baeldung;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.SpringCloudConfigClientApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringCloudConfigClientApplication.class)
public class SpringContextLiveTest {

	@Test
	public void contextLoads() {
	}

}
