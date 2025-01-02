package com.baeldung.mockito.wantedbutnotinvocked;

class Main {

    Helper helper = new Helper();

    String methodUnderTest(int i) {
        if (i > 5) {
            return helper.getBaeldungString();
        }
        return "Hello";
    }

}
