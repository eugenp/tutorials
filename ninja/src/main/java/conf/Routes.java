package conf;

import ninja.AssetsController;
import ninja.Router;
import ninja.application.ApplicationRoutes;
import controllers.ApplicationController;

public class Routes implements ApplicationRoutes {

    @Override
    public void init(Router router) {  
        
        router.GET().route("/").with(ApplicationController::index);
        router.GET().route("/hello").with(ApplicationController::helloWorld);
        router.GET().route("/userJson").with(ApplicationController::userJson);
        router.GET().route("/users").with(ApplicationController::fetchUsers);
 
        //Assets
        router.GET().route("/assets/webjars/{fileName: .*}").with(AssetsController::serveWebJars);
        router.GET().route("/assets/{fileName: .*}").with(AssetsController::serveStatic);
        
        //Index
        router.GET().route("/.*").with(ApplicationController::index);
       
    }

}
