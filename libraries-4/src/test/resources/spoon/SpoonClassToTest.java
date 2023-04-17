package spoon;

import some.superduper.HelperClass;
import some.superduper.SomeVO;

public class SpoonClassToTest {
    
    private static HelperClass helper;
    
    public SpoonClassToTest() {}
    
    public int publicMethod() {}
    
    private int internalStuff() {}
    
    protected SomeVO protectedMethod() {
        return new SomeVO();
    }
    
    void packageProtectedMethod() {
        
        try {
            HelperClass.callSomeMethodThatThrows();
        }
        catch(Exception ignored) {}
    }
    
}