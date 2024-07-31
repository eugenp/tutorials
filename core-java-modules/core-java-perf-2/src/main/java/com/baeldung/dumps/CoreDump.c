#include <jni.h>
#include "CoreDump.h"

void core() {
    int *p = NULL;
    *p = 0;
}

JNIEXPORT void JNICALL Java_CoreDump_core (JNIEnv *env, jobject obj) {
    core();
};

void main() {
}

