package com.mycompany.userdetails.domain;

import com.mycompany.userdetails.infrastructure.ConsoleAdapter;
import com.mycompany.userdetails.infrastructure.PrintServiceImpl;
import com.mycompany.userdetails.infrastructure.UserRepostitoryImpl;

public class Application {
    
    public static void main(String[] args) {
        
        // Instantiate right side adapter
        IUserRepository userRepository = new UserRepostitoryImpl();
        
        //Instantiate the hexagon        
        IUserService userService = new UserDetails(userRepository);
        
        IPrint print = new PrintServiceImpl();
        ConsoleAdapter consoleAdapter = new ConsoleAdapter(userService, print);
        consoleAdapter.get(10L);
    }
}
