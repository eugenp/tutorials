package com.sql;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.sql.database.DatabaseBean;

public class Main {

    public static void main(String[] args) {
        
        Logger logger = LoggerFactory.getLogger(DatabaseBean.class);
        
        ApplicationContext context = 
            new ClassPathXmlApplicationContext(new String[] { "beans.xml" });
        DatabaseBean databaseBean = (DatabaseBean) context.getBean("databaseBean");
        
        String credentials = databaseBean.getLogin()
            .concat(":")
            .concat(databaseBean.getPassword().getStr())
            .concat("@")
            .concat(databaseBean.getDatabasePath())
            .concat(":")
            .concat(Integer.toString(databaseBean.getPort()));
        
        logger.info(credentials);
        
    }

}
