package com.baeldung.primitive;

public class BenchmarkRunner {

    public static void main(String[] args) throws Exception {
        new IntPrimitiveLookup().run();
        new IntegerWrapperLookup().run();
        new FloatPrimitiveLookup().run();
        new FloatWrapperLookup().run();
        new DoublePrimitiveLookup().run();
        new DoubleWrapperLookup().run();
        new ShortPrimitiveLookup().run();
        new ShortWrapperLookup().run();
        new BooleanPrimitiveLookup().run();
        new BooleanWrapperLookup().run();
        new CharPrimitiveLookup().run();
        new CharacterWrapperLookup().run();
        new BytePrimitiveLookup().run();
        new ByteWrapperLookup().run();
        new LongPrimitiveLookup().run();
        new LongWrapperLookup().run();
    }
}
