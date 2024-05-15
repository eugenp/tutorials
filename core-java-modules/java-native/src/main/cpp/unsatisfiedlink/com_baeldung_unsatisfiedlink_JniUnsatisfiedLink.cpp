#include "com_baeldung_unsatisfiedlink_JniUnsatisfiedLink.h"
#include <iostream>

/*
 * Class:     com_baeldung_unsatisfiedlink_JniUnsatisfiedLink
 * Method:    test
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_baeldung_unsatisfiedlink_JniUnsatisfiedLink_test (JNIEnv* env, jobject thisObject) {
	std::string test = "Test OK";
    std::cout << test << std::endl;
    return env->NewStringUTF(test.c_str());
}
