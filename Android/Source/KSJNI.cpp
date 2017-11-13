#include "KSJNI.h"

static JNIEnv *g_jniEnv;


extern "C" void ksjni_init(JNIEnv *jniEnv) {
    g_jniEnv = jniEnv;
}

extern "C" JNIEnv *ksjni_getEnv() {
    return g_jniEnv;
}
