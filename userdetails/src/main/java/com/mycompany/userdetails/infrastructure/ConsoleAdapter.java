package com.mycompany.userdetails.infrastructure;

import com.mycompany.userdetails.domain.IPrint;
import com.mycompany.userdetails.domain.IUserService;

public class ConsoleAdapter {
    
    private IUserService userService;
    private IPrint print;

    public ConsoleAdapter(IUserService userService, IPrint print) {
        super();
        this.userService = userService;
        this.print = print;
    }

    public void get(Long id) {
        String userDetails = userService.getUserDetails(id);
        print.displayDetails(userDetails); 
    }
   
    

}
