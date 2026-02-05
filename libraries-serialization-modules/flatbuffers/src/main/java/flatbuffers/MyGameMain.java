package com.baeldung.flatbuffers;

import static MyGame.terrains.Terrain.addColor;
import static MyGame.terrains.Terrain.addEffects;
import static MyGame.terrains.Terrain.addNavigation;
import static MyGame.terrains.Terrain.endTerrain;
import static MyGame.terrains.Terrain.startTerrain;

import java.nio.ByteBuffer;

import com.google.flatbuffers.FlatBufferBuilder;

import MyGame.terrains.Effect;
import MyGame.terrains.Terrain;

public class MyGameMain {

    public byte[] serialiseDesertTerrainData() {
        int INITIAL_BUFFER = 1024;
        FlatBufferBuilder builder = new FlatBufferBuilder(INITIAL_BUFFER);

        int sandstormOffset = builder.createString("Sandstorm");
        short damage = 3;
        int sandStorm = MyGame.terrains.Effect.createEffect(builder, sandstormOffset, damage);

        int droughtOffset = builder.createString("Drought");
        short damageDrought = 1;
        int drought = MyGame.terrains.Effect.createEffect(builder, droughtOffset, damageDrought);
        int[] effects = new int[2];
        effects[0] = sandStorm;
        effects[1] = drought;

        byte color = MyGame.terrains.Color.Brown;
        int terrainName = builder.createString("Desert");
        int navigationName = builder.createString("south");

        int effectOffset = MyGame.terrains.Terrain.createEffectsVector(builder, effects);

        startTerrain(builder);
        MyGame.terrains.Terrain.addName(builder, terrainName);
        addColor(builder, color);
        addNavigation(builder, navigationName);
        addEffects(builder, effectOffset);
        int desert = endTerrain(builder);
        builder.finish(desert);

        return builder.sizedByteArray();
    }

    public MyGame.terrains.Terrain deserialiseDataToTerrain(byte[] buffer) {
        ByteBuffer buf = ByteBuffer.wrap(buffer);
        return Terrain.getRootAsTerrain(buf);
    }

    public Effect.Vector deserialiseDataToEffect(byte[] buffer) {
        ByteBuffer buf = ByteBuffer.wrap(buffer);

        return Terrain.getRootAsTerrain(buf)
          .effectsVector();
    }
}
