package com.baeldung.jmonkeyengine;

import com.jme3.app.SimpleApplication;
import com.jme3.app.StatsAppState;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;

public class UserInputApplication extends SimpleApplication {
    private boolean rotationEnabled = false;

    public static void main(String[] args) {
        UserInputApplication app = new UserInputApplication();
        app.start();
    }

    public UserInputApplication() {
        super(new StatsAppState());
    }

    @Override
    public void simpleInitApp() {
        Box mesh = new Box(1, 2, 3);

        Geometry geometry = new Geometry("Box", mesh);

        Material material = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        material.setColor("Color", ColorRGBA.Red);
        geometry.setMaterial(material);

        Node rotation = new Node("rotation");

        rotation.attachChild(geometry);
        rootNode.attachChild(rotation);

        inputManager.addMapping("Rotate", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_J));
        inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_K));

        ActionListener actionListener = new ActionListener() {
            @Override
            public void onAction(String name, boolean isPressed, float tpf) {
                if (name.equals("Rotate") && !isPressed) {
                    rotationEnabled = !rotationEnabled;
                }
            }
        };

        AnalogListener analogListener = new AnalogListener() {
            @Override
            public void onAnalog(String name, float value, float tpf) {
                if (name.equals("Left")) {
                    rotation.rotate(0, -tpf, 0);
                } else if (name.equals("Right")) {
                    rotation.rotate(0, tpf, 0);
                }
            }
        };

        inputManager.addListener(actionListener, "Rotate");
        inputManager.addListener(analogListener, "Left", "Right");
    }

    @Override
    public void simpleUpdate(float tpf) {
        if (rotationEnabled) {
            Spatial rotation = rootNode.getChild("rotation");
            rotation.rotate(0, 0, tpf);
        }
    }

}
