#ifndef HDR_KSJNI_h
#define HDR_KSJNI_h

#include <jni.h>

#ifdef __cplusplus
extern "C" {
#endif

void ksjni_init(JNIEnv *jniEnv);

JNIEnv *ksjni_getEnv();

#ifdef __cplusplus
}
#endif

#endif // HDR_KSJNI_h
