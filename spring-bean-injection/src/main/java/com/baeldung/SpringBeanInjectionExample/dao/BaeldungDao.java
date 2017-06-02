package com.baeldung.SpringBeanInjectionExample.dao;

/**
 * Created by smartkit on 24/05/2017.
 */
public class BaeldungDao {
        //simple property
        private String wordpress;
        //setter
        public void setWordpress(String wordpress) {
                this.wordpress = wordpress;
        }
        //getter
        public String getWordpress() {
                return wordpress;
        }
}
