# Create the header with javac -h . ClassName.java
# Remember to set your JAVA_HOME env var
g++ -c -fPIC -I${JAVA_HOME}/include -I${JAVA_HOME}/include/linux com_baeldung_jni_HelloWorldJNI.cpp -o com_baeldung_jni_HelloWorldJNI.o
g++ -c -fPIC -I${JAVA_HOME}/include -I${JAVA_HOME}/include/linux com_baeldung_jni_ExampleParametersJNI.cpp -o com_baeldung_jni_ExampleParametersJNI.o
g++ -c -fPIC -I${JAVA_HOME}/include -I${JAVA_HOME}/include/linux com_baeldung_jni_ExampleObjectsJNI.cpp -o com_baeldung_jni_ExampleObjectsJNI.o
g++ -shared -fPIC -o ../../../native/linux_x86_64/libnative.so com_baeldung_jni_HelloWorldJNI.o com_baeldung_jni_ExampleParametersJNI.o com_baeldung_jni_ExampleObjectsJNI.o -lc
# Don't forget to set java.library.path to point to the folder where you have the libnative you're loading.