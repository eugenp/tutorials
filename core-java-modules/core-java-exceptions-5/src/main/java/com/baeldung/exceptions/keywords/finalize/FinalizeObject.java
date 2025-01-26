package com.baeldung.exceptions.keywords.finalize;

public class FinalizeObject {

    @Override
    protected void finalize() throws Throwable {
        System.out.println("Execute finalize method");
        super.finalize();
    }

    public static void main(String[] args) throws Exception {
        FinalizeObject object = new FinalizeObject();
        object = null;
        System.gc();
        Thread.sleep(1000);
    }

}
