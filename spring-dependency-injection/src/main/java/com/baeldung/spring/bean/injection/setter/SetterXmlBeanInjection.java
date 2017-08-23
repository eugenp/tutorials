package com.baeldung.spring.bean.injection.setter;

import com.baeldung.spring.bean.injection.service.ReaderService;
import com.baeldung.spring.bean.injection.service.WriterService;

public class SetterXmlBeanInjection {

        private ReaderService readerService;
        private WriterService writerService;

        public String readData() {
                return readerService.read();
        }

        public String writeData(String data) {
                return writerService.write(data);
        }

        public SetterXmlBeanInjection setReaderService(ReaderService readerService) {
                this.readerService = readerService;
                return this;
        }

        public SetterXmlBeanInjection setWriterService(WriterService writerService) {
                this.writerService = writerService;
                return this;
        }
}
