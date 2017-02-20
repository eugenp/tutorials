package com.sql;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.sql.database.DatabaseBean;
import com.sql.security.MySecureBean;

public class Main {

    public static void main(String[] args) {

        Logger logger = LoggerFactory.getLogger(DatabaseBean.class);

        ApplicationContext context 
        = new AnnotationConfigApplicationContext(DatabaseBean.class, MySecureBean.class);
        DatabaseBean databaseBean = context.getBean(DatabaseBean.class);

        String credentials = databaseBean.getLogin()
            .concat(":")
            .concat(databaseBean.getPassword()
                .getStr())
            .concat("@")
            .concat(databaseBean.getDatabasePath())
            .concat(":")
            .concat(databaseBean.getPort());

        logger.info(credentials);

    }

}
