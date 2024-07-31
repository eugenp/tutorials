package com.baeldung.lambdacalls;

import static com.baeldung.lambdacalls.LambdaExample.*;
import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class LambdaCallsUnitTest {

    @Mock
    private/**/ BrickLayer brickLayer;
    @InjectMocks
    private LambdaExample lambdaExample;

    @Test
    public void whenCallingALambda_thenTheInvocationCanBeConfirmedWithCorrectArguments(){
        String bricks = "red bricks";
        lambdaExample.createWall(bricks);
        verify(brickLayer).layBricks(bricks);
    }

    @Test
    public void whenCallingALambda_thenCorrectBehaviourIsPerformed(){
        LambdaExample lambdaExample = new LambdaExample();
        String bricks = "red bricks";

        lambdaExample.createWall(bricks);
        ArrayList<String> bricksList = lambdaExample.getBricksList();

        assertEquals(bricks, bricksList.get(0));
    }

}
