package com.baeldung.spring.bean.injection.setter;

import com.baeldung.spring.bean.injection.service.ReaderService;
import com.baeldung.spring.bean.injection.service.WriterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component public class SetterAnnotationBeanInjection {

        private ReaderService readerService;
        private WriterService writerService;

        public String readData() {
                return readerService.read();
        }

        public String writeData(String data) {
                return writerService.write(data);
        }

        @Autowired public SetterAnnotationBeanInjection setReaderService(ReaderService readerService) {
                this.readerService = readerService;
                return this;
        }

        @Autowired public SetterAnnotationBeanInjection setWriterService(WriterService writerService) {
                this.writerService = writerService;
                return this;
        }
}
