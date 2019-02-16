package com.mycompany.userdetails.infrastructure;

import com.mycompany.userdetails.domain.IPrint;

public class PrintServiceImpl implements IPrint {

    public void displayDetails(String text) {
        System.out.print(text);
        
    }

}
