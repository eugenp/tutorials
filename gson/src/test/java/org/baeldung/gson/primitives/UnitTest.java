package org.baeldung.gson.primitives;

import com.google.gson.*;
import org.baeldung.gson.primitives.models.*;
import org.junit.Test;

import java.lang.reflect.Type;

import static junit.framework.TestCase.*;

public class UnitTest {
    @Test public void toJsonAllPrimitives() {
        PrimitiveBundle primitiveBundle = new PrimitiveBundle();

        // @formatter:off
        primitiveBundle.byteValue    = (byte) 0x00001111;
        primitiveBundle.shortValue   = (short) 3;
        primitiveBundle.intValue     = 3;
        primitiveBundle.longValue    = 3;
        primitiveBundle.floatValue   = 3.5f;
        primitiveBundle.doubleValue  = 3.5;
        primitiveBundle.booleanValue = true;
        primitiveBundle.charValue    = 'a';
        // @formatter:on

        Gson gson = new Gson();

        String expected = "{\"byteValue\":17,\"shortValue\":3,\"intValue\":3," + "\"longValue\":3,\"floatValue\":3.5" + ",\"doubleValue\":3.5" + ",\"booleanValue\":true,\"charValue\":\"a\"}";

        assertEquals(expected, gson.toJson(primitiveBundle));
    }

    @Test public void fromJsonAllPrimitives() {
        String json = "{\"byteValue\": 17, \"shortValue\": 3, \"intValue\": 3, " + "\"longValue\": 3, \"floatValue\": 3.5" + ", \"doubleValue\": 3.5" + ", \"booleanValue\": true, \"charValue\": \"a\"}";

        Gson gson = new Gson();
        PrimitiveBundle model = gson.fromJson(json, PrimitiveBundle.class);

        // @formatter:off
        assertEquals(17,  model.byteValue);
        assertEquals(3,   model.shortValue);
        assertEquals(3,   model.intValue);
        assertEquals(3,   model.longValue);
        assertEquals(3.5, model.floatValue, 0.0001);
        assertEquals(3.5, model.doubleValue, 0.0001);
        assertTrue(       model.booleanValue);
        assertEquals('a', model.charValue);
        // @formatter:on
    }

    @Test public void toJsonByteToBitString() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(ByteExample.class, new GsonBitStringSerializer());

        Gson gson = builder.create();
        ByteExample model = new ByteExample();
        model.value = (byte) 0b1111;

        assertEquals("{\"value\":\"1111\"}", gson.toJson(model));
    }

    @Test public void fromJsonByteFromBitString() {
        String json = "{\"value\": \"1111\"}";
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(ByteExample.class, new GsonBitStringDeserializer());

        Gson gson = gsonBuilder.create();

        ByteExample model = gson.fromJson(json, ByteExample.class);

        assertEquals(15, model.value);
    }

    @Test public void fromJsonPrecissionMismatch() {
        String json = "{\"value\": 12.123456789123456}";
        Gson gson = new Gson();
        FloatExample model = gson.fromJson(json, FloatExample.class);
        assertEquals(12.123457f, model.value, 0.000001);
    }

    @Test public void fromJsonOverflow() {
        Gson gson = new Gson();
        String json = "{\"value\": \"300\"}";
        ByteExample model = gson.fromJson(json, ByteExample.class);

        assertEquals(44, model.value);
    }

    @Test public void fromJsonNonCompatibleNumberTypes() {
        Gson gson = new Gson();
        String json = "{\"value\": 2.3}";
        try {
            gson.fromJson(json, ByteExample.class);
        } catch (Exception ex) {
            assertTrue(ex instanceof JsonSyntaxException);
            assertTrue(ex.getCause() instanceof NumberFormatException);
            return;
        }

        fail();
    }

    @Test public void fromJsonUnicodeChar() {
        Gson gson = new Gson();
        String json = "{\"value\": \"\\u00AE\"}";
        CharExample model = gson.fromJson(json, CharExample.class);

        assertEquals('\u00AE', model.value);
    }

    @Test public void fromJsonNull() {
        Gson gson = new Gson();
        String json = "{\"value\": null}";
        ByteExample model = gson.fromJson(json, ByteExample.class);

        assertEquals(1, model.value);
    }

    @Test(expected = JsonSyntaxException.class) public void fromJsonEmptyString() {
        Gson gson = new Gson();
        String json = "{\"value\": \"\"}";
        gson.fromJson(json, ByteExample.class);
    }

    @Test public void fromJsonValidValueWithinString() {
        Gson gson = new Gson();
        String json = "{\"value\": \"15\"}";
        ByteExample model = gson.fromJson(json, ByteExample.class);

        assertEquals(15, model.value);
    }

    @Test(expected = JsonSyntaxException.class) public void fromJsonInvalidValueWithinString() {
        Gson gson = new Gson();
        String json = "{\"value\": \"15x\"}";
        gson.fromJson(json, ByteExample.class);
    }

    @Test(expected = JsonSyntaxException.class) public void fromJsonInvalidValueNotInAString() {
        Gson gson = new Gson();
        String json = "{\"value\": s15s}";
        gson.fromJson(json, ByteExample.class);
    }

    @Test public void fromJsonBooleanFrom2ValueInteger() {
        String json = "{\"value\": 1}";
        Gson gson = new Gson();

        try {
            gson.fromJson(json, BooleanExample.class);
        } catch (Exception ex) {
            assertTrue(ex instanceof JsonSyntaxException);
            assertTrue(ex.getCause() instanceof IllegalStateException);
            return;
        }

        fail();
    }

    @Test public void fromJsonBooleanFrom2ValueIntegerSolution() {
        String json = "{\"value\": 1}";
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(BooleanExample.class, new BooleanAs2ValueIntegerDeserializer());

        Gson gson = builder.create();

        BooleanExample model = gson.fromJson(json, BooleanExample.class);

        assertTrue(model.value);
    }

    @Test public void fromJsonBooleanFromYes() {
        String json = "{\"value\": yes}";
        Gson gson = new Gson();

        BooleanExample model = gson.fromJson(json, BooleanExample.class);

        // pay attention here that we are deserializing yes.
        assertFalse(model.value);
    }

    @Test public void fromJsonBooleanFromInvalidValue() {
        String json = "{\"value\": \"15x\"}";
        Gson gson = new Gson();

        BooleanExample model = gson.fromJson(json, BooleanExample.class);

        assertFalse(model.value);
    }

    // @formatter:off
    static class GsonBitStringDeserializer implements JsonDeserializer<ByteExample> {
        @Override public ByteExample deserialize(
            JsonElement jsonElement,
            Type type,
            JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

            ByteExample byteExample = new ByteExample();
            byteExample.value = (byte) Integer.parseInt(
                                                  jsonElement.getAsJsonObject()
                                                             .getAsJsonPrimitive("value")
                                                             .getAsString()
                                                  , 2);
            return byteExample;
        }
    }

    static class GsonBitStringSerializer implements JsonSerializer<ByteExample> {
        @Override public JsonElement serialize(
            ByteExample model,
            Type type,
            JsonSerializationContext jsonSerializationContext) {

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("value", Integer.toBinaryString(model.value));
            return jsonObject;
        }
    }

    static class BooleanAs2ValueIntegerDeserializer implements JsonDeserializer<BooleanExample> {
        @Override public BooleanExample deserialize(
            JsonElement jsonElement,
            Type type,
            JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

            BooleanExample model = new BooleanExample();
            int value = jsonElement.getAsJsonObject().getAsJsonPrimitive("value").getAsInt();
            if (value == 0) {
                model.value = false;
            } else if (value == 1) {
                model.value = true;
            } else {
                throw new JsonParseException("Unexpected value. Trying to deserialize "
                    + "a boolean from an integer different than 0 and 1.");
            }

            return model;
        }
    }
    // @formatter:on
}
