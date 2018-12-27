package com.baeldung;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.custom.CustomApplication;
import com.baeldung.produceimage.ImageApplication;
import com.baeldung.propertyeditor.PropertyEditorApplication;
import com.baeldung.responseheaders.ResponseHeadersApplication;
import com.baeldung.sampleapp.config.MainApplication;
import com.baeldung.web.log.app.Application;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { CustomApplication.class, ImageApplication.class, PropertyEditorApplication.class,
		ResponseHeadersApplication.class, Application.class, com.baeldung.web.upload.app.UploadApplication.class,
		MainApplication.class})
public class SpringContextIntegrationTest {

    @Test
    public void whenSpringContextIsBootstrapped_thenNoExceptions() {	
    }
}
