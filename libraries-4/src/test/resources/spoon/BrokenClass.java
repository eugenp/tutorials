package spoon;


public class BrokenClass {
    
    
    public BrokenClass() { 
        2 = anx;
    }

    // Syntax error
    provate void privateMethod() {}
    
    // Syntax error
    public void publicMethod() {}
    
}