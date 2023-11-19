package com.baeldung.spoon;

import spoon.Launcher;
import spoon.SpoonAPI;
import spoon.reflect.CtModel;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;
import spoon.support.sniper.SniperJavaPrettyPrinter;

/**
 * Loads a given class and creates a summary o
 */
public class ClassReporter {
    

    public MethodSummary generateMethodSummaryReport(String source) {
        
        SpoonAPI spoon = new Launcher();
        spoon.addInputResource(source);
        CtModel model = spoon.buildModel();        
        MethodSummary report = new MethodSummary();                        
        model.filterChildren((el) -> el instanceof CtClass<?>)
          .forEach((CtClass<?> clazz) -> processMethods(report,clazz));
                
        return report;
    }
    
    private void processMethods(MethodSummary report, CtClass<?> ctClass) {
                
        ctClass.filterChildren((c) -> c instanceof CtMethod<?> )
          .forEach((CtMethod<?> m) -> {  
              if (m.isPublic()) {
                  report.addPublicMethod();
              }
              else if ( m.isPrivate()) {
                  report.addPrivateMethod();
              }
              else if ( m.isProtected()) {
                  report.addProtectedMethod();
              }
              else {
                  report.addPackagePrivateMethod();
              }                         
          });      
    }


    public static class MethodSummary {
        private int totalMethodCount;
        private int publicMethodCount;
        private int protectedMethodCount;
        private int packagePrivateMethodCount;
        private int privateMethodCount;
                
        void addPublicMethod() {
            totalMethodCount++;
            publicMethodCount++;
        }

        void addPrivateMethod() {
            totalMethodCount++;
            privateMethodCount++;
        }

        void addProtectedMethod() {
            totalMethodCount++;
            protectedMethodCount++;
        }

        void addPackagePrivateMethod() {
            totalMethodCount++;
            packagePrivateMethodCount++;
        }
        
        /**
         * @return the totalMethodCount
         */
        public int getTotalMethodCount() {
            return totalMethodCount;
        }

        /**
         * @return the publicMethodCount
         */
        public int getPublicMethodCount() {
            return publicMethodCount;
        }

        /**
         * @return the protectedMethodCount
         */
        public int getProtectedMethodCount() {
            return protectedMethodCount;
        }

        /**
         * @return the privateMethodCount
         */
        public int getPrivateMethodCount() {
            return privateMethodCount;
        }

        /**
         * @return the privateMethodCount
         */
        public int getPackagePrivateMethodCount() {
            return packagePrivateMethodCount;
        }

    }
}
