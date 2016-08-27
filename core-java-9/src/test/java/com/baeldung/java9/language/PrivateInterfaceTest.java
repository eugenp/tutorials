package com.baeldung.java9;

import com.baeldung.java9.language.PrivateInterface;
import com.baeldung.java9.language.TryWithResourcesTest;

public class Main {

    public static void main(String args[]){
        PrivateInterface pi =new PrivateInterface() {
        };
        pi.check();
    }
    
//    public static void main(String[] args) throws Exception {
//        MultiResultionImageTest mri = new MultiResultionImageTest();
//        mri.baseMultiResImageTest();
//
//        TryWithResourcesTest tt = new TryWithResourcesTest();
// //       tt.test1();
//    }

}
