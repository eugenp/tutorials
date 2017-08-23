package com.baeldung.spring.bean.injection.constructor;

import com.baeldung.spring.bean.injection.service.ReaderService;
import com.baeldung.spring.bean.injection.service.WriterService;
import org.springframework.beans.factory.annotation.Autowired;

public class ConstructorXmlBeanInjection {

        private ReaderService readerService;
        private WriterService writerService;

        @Autowired
        public ConstructorXmlBeanInjection(ReaderService readerService, WriterService writerService) {
                this.readerService = readerService;
                this.writerService = writerService;
        }

        public String readData() {
                return readerService.read();
        }

        public String writeData(String data) {
                return writerService.write(data);
        }
}
