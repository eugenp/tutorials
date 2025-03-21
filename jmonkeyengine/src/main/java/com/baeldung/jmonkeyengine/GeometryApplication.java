package com.baeldung.jmonkeyengine;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;

public class GeometryApplication extends SimpleApplication {

    public static void main(String[] args) {
        GeometryApplication app = new GeometryApplication();
        app.start();
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
    }

    @Override
    public void simpleUpdate(float tpf) {
        Spatial rotation = rootNode.getChild("rotation");
        rotation.rotate(0, tpf, 0);
    }
}
