package controllers;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import ninja.NinjaTest;
import ninja.Result;
import services.UserService;

<<<<<<< HEAD:web-modules/ninja/src/test/java/controllers/ApiControllerMockIntegrationTest.java
@RunWith(NinjaRunner.class)
public class ApiControllerMockIntegrationTest {
=======
public class ApiControllerMockUnitTest extends NinjaTest {
>>>>>>> cb71de5be1a61279b4ac230c5ddde1cd1017ea42:web-modules/ninja/src/test/java/controllers/ApiControllerMockUnitTest.java

    private UserService userService;

    private ApplicationController applicationController;

    @Before
    public void setupTest() {
        userService = this.ninjaTestServer.getInjector().getInstance(UserService.class);
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
