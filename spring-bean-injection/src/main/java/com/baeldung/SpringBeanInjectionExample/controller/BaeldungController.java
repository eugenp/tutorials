package com.baeldung.SpringBeanInjectionExample.controller;

import com.baeldung.SpringBeanInjectionExample.service.BaeldungService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by smartkit on 24/05/2017.
 */
public class BaeldungController {

        private BaeldungService baeldungService;

        @Autowired
        public BaeldungController (BaeldungService baeldungService){
                this.baeldungService = baeldungService;
        }
}
