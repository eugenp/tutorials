package flatbuffers;

import org.junit.Assert;
import org.junit.Test;

import com.baeldung.flatbuffers.MyGameMain;

import MyGame.terrains.Effect;

public class FlatBufferGameUnitTest {

    @Test
    public void givenTerrainEffect_whenSerialisedWithValues_returnBytes() {
        MyGameMain myGame = new MyGameMain();
        byte[] bytes = myGame.serialiseDesertTerrainData();
        Assert.assertNotNull(bytes);
    }

    @Test
    public void givenSerialisedBytes_whenDeSerialised_returnsTerrain() {
        MyGameMain myGame = new MyGameMain();
        byte[] bytes = myGame.serialiseDesertTerrainData();

        MyGame.terrains.Terrain terrain = myGame.deserialiseDataToTerrain(bytes);
        Assert.assertNotNull(terrain);
        Assert.assertEquals(terrain.name(), "Desert");
        Assert.assertEquals(terrain.navigation(), "south");
        Assert.assertEquals(MyGame.terrains.Color.name(terrain.color()), "Brown");
    }

    @Test
    public void givenSerialisedBytes_whenDeSerialised_returnsTerrainEffect() {
        MyGameMain myGame = new MyGameMain();
        byte[] bytes = myGame.serialiseDesertTerrainData();

        Effect.Vector effectsVector = myGame.deserialiseDataToEffect(bytes);
        Assert.assertNotNull(effectsVector);
        Assert.assertEquals(effectsVector.get(0).name(), "Sandstorm");
        Assert.assertEquals(effectsVector.get(1).name(), "Drought");

        Assert.assertEquals(effectsVector.get(1).damage(), 1);
    }
}
