
#include "KSID.h"
#include "KSJNI.h"

#include <stdbool.h>
#include <stdio.h>

static jclass g_uuidClass;
static jmethodID g_newGuidMethod;
static jmethodID g_msbMethod;
static jmethodID g_lsbMethod;


static void init() {
    static bool isInitialized = false;
    if (!isInitialized) {
        isInitialized = true;
        JNIEnv *env = ksjni_getEnv();
        g_uuidClass = env->FindClass("java/util/UUID");
        g_newGuidMethod = env->GetStaticMethodID(g_uuidClass, "randomUUID", "()Ljava/util/UUID;");
        g_msbMethod = env->GetMethodID(g_uuidClass, "getMostSignificantBits", "()J");
        g_lsbMethod = env->GetMethodID(g_uuidClass, "getLeastSignificantBits", "()J");
    }
}

void ksid_generate(char *destinationBuffer37Bytes) {
    init();
    // TODO: Figure out why this doesn't work.
//    JNIEnv* env = ksjni_getEnv();
//    jobject javaUuid = env->CallStaticObjectMethod(g_uuidClass, g_newGuidMethod);
//    jlong msb = env->CallLongMethod(javaUuid, g_msbMethod);
//    jlong lsb = env->CallLongMethod(javaUuid, g_lsbMethod);
    jlong msb = 1;
    jlong lsb = 1;

    sprintf(destinationBuffer37Bytes,
            "%02X%02X%02X%02X-%02X%02X-%02X%02X-%02X%02X-%02X%02X%02X%02X%02X%02X",
            (unsigned) (msb >> 56) & 0xff,
            (unsigned) (msb >> 48) & 0xff,
            (unsigned) (msb >> 40) & 0xff,
            (unsigned) (msb >> 32) & 0xff,
            (unsigned) (msb >> 24) & 0xff,
            (unsigned) (msb >> 16) & 0xff,
            (unsigned) (msb >> 8) & 0xff,
            (unsigned) msb & 0xff,
            (unsigned) (lsb >> 56) & 0xff,
            (unsigned) (lsb >> 48) & 0xff,
            (unsigned) (lsb >> 40) & 0xff,
            (unsigned) (lsb >> 32) & 0xff,
            (unsigned) (lsb >> 24) & 0xff,
            (unsigned) (lsb >> 16) & 0xff,
            (unsigned) (lsb >> 8) & 0xff,
            (unsigned) lsb & 0xff
    );
}
