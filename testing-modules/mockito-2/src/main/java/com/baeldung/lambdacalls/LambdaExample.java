package com.baeldung.lambdacalls;

import java.util.ArrayList;

public class LambdaExample {

    private BrickLayer brickLayer = new BrickLayer();

    static ArrayList<String> bricksList = new ArrayList<>();

    public void createWall(String bricks){
        Runnable build = () -> brickLayer.layBricks(bricks);
        build.run();
    }

    public ArrayList<String> getBricksList(){
        return bricksList;
    }

    static class BrickLayer {
        void layBricks(String bricks){
            bricksList.add(bricks);
        }
    }

}
