package com.baeldung;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.produceimage.ImageApplication;
import com.baeldung.responseheaders.ResponseHeadersApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { ImageApplication.class,
		ResponseHeadersApplication.class, com.baeldung.web.upload.app.UploadApplication.class,
		})
public class SpringContextIntegrationTest {

    @Test
    public void whenSpringContextIsBootstrapped_thenNoExceptions() {	
    }
}
