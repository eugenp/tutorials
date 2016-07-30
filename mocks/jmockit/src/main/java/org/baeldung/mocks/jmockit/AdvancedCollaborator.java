package org.baeldung.mocks.jmockit;

public class AdvancedCollaborator {
    public AdvancedCollaborator(){}
    public AdvancedCollaborator(String string) throws Exception{
        i = string.length();
    }
    public String methodThatCallsPrivateMethod(int i){
        return privateMethod() + i;
    }
    private String privateMethod(){
        return "default:";
    }
}
