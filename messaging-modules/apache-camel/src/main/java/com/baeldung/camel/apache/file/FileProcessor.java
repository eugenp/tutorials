package com.baeldung.camel.apache.file;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
public class FileProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        String originalFileName = (String) exchange.getIn().getHeader(Exchange.FILE_NAME, String.class);

        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String changedFileName = dateFormat.format(date) + originalFileName;
        exchange.getIn().setHeader(Exchange.FILE_NAME, changedFileName);
    }

}
