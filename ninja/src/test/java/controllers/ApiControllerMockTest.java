package controllers;

import static org.junit.Assert.assertEquals;
import javax.inject.Inject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import ninja.NinjaRunner;
import ninja.Result;
import services.UserService;

@RunWith(NinjaRunner.class)
public class ApiControllerMockTest {

    @Inject private UserService userService;

    ApplicationController applicationController;

    @Before
    public void setupTest() {
        applicationController = new ApplicationController();
        applicationController.userService = userService;
    }

    @Test
    public void testThatGetUserJson() {
        Result result = applicationController.userJson();
        System.out.println(result.getRenderable());
        assertEquals(userService.getUserMap().toString(), result.getRenderable().toString());
    }
    
}
