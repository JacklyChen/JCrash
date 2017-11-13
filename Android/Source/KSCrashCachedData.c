#include "KSCrashCachedData.h"

//#define KSLogger_LocalLevel TRACE
#include "KSLogger.h"

void ksccd_init(__unused int pollingIntervalInSeconds) {
}

void ksccd_freeze() {
}

void ksccd_unfreeze() {
}

KSThread *ksccd_getAllThreads(__unused int *threadCount) {
    return NULL;
}

const char *ksccd_getThreadName(__unused KSThread thread) {
    return NULL;
}

const char *ksccd_getQueueName(__unused KSThread thread) {
    return NULL;
}
