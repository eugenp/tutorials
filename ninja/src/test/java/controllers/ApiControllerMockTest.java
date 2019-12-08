package controllers;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;

import javax.inject.Inject;

import org.doctester.testbrowser.Request;
import org.doctester.testbrowser.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

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
        assertEquals(userService.getUser().toString(), result.getRenderable().toString());
    }
    
}
