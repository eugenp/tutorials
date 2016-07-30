package org.baeldung.mocks.jmockit;

public class AdvancedCollaborator {
    public String methodThatCallsPrivateMethod(int i){
        return privateMethod() + i;
    }
    private String privateMethod(){
        return "default:";
    }
}
