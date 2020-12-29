package com.baeldung.functional;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ReferentialTransparency {

    private static Logger logger = Logger.getGlobal();

    public void main() {

        String data = new SimpleData().setData("Baeldung")
            .getData();
        logger.log(Level.INFO, new SimpleData().setData("Baeldung")
            .getData());
        logger.log(Level.INFO, data);
        logger.log(Level.INFO, "Baeldung");
    }

    public class SimpleData {

        private Logger logger = Logger.getGlobal();

        private String data;

        public String getData() {
            logger.log(Level.INFO, "Get data called for SimpleData");
            return data;
        }

        public SimpleData setData(String data) {
            logger.log(Level.INFO, "Set data called for SimpleData");
            this.data = data;
            return this;
        }

    }

}
