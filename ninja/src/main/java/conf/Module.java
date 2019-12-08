package conf;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

import services.UserService;
import services.UserServiceImpl;

@Singleton
public class Module extends AbstractModule {
    
    protected void configure() {
        
        bind(UserService.class).to(UserServiceImpl.class);
        
    }

}
