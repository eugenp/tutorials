package com.baeldung.spring.bean.injection.constructor;

import com.baeldung.spring.bean.injection.service.ReaderService;
import com.baeldung.spring.bean.injection.service.WriterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConstructorAnnotationBeanInjection {

        private ReaderService readerService;
        private WriterService writerService;

        @Autowired
        public ConstructorAnnotationBeanInjection(ReaderService readerService, WriterService writerService) {
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
