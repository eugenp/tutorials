package com.baeldung.jmockit;

public class AdvancedCollaborator {
    int i;
    private int privateField = 5;
    public AdvancedCollaborator(){}
    public AdvancedCollaborator(String string) throws Exception{
        i = string.length();
    }
    public String methodThatCallsProtectedMethod(int i){
        return protectedMethod() + i;
    }
    public int methodThatReturnsThePrivateField(){
        return privateField;
    }
    protected String protectedMethod(){
        return "default:";
    }
    class InnerAdvancedCollaborator{}
}
