
#include "KSMachineContext.h"
#include "KSSystemCapabilities.h"
#include "KSCPU.h"
#include "KSStackCursor_MachineContext.h"
#include "KSLogger.h"

static KSThread g_reservedThreads[10];
static int g_reservedThreadsMaxIndex = sizeof(g_reservedThreads) / sizeof(g_reservedThreads[0]) - 1;
static int g_reservedThreadsCount = 0;


typedef struct KSMachineContext {
    // TODO
} KSMachineContext;

static inline bool isStackOverflow(const KSMachineContext *const context) {
    KSStackCursor stackCursor;
    kssc_initWithMachineContext(&stackCursor, KSSC_STACK_OVERFLOW_THRESHOLD, context);
    while (stackCursor.advanceCursor(&stackCursor)) {
    }
    return stackCursor.state.hasGivenUp;
}

int ksmc_contextSize() {
    return sizeof(KSMachineContext);
}

KSThread ksmc_getThreadFromContext(const KSMachineContext *const context) {
    // TODO
    return 0;
}

bool ksmc_getContextForThread(KSThread thread, KSMachineContext *destinationContext,
                              bool isCrashedContext) {
    // TODO
    return false;
}

bool ksmc_getContextForSignal(void *signalUserContext, KSMachineContext *destinationContext) {
    // TODO
    return false;
}

void ksmc_addReservedThread(KSThread thread) {
    int nextIndex = g_reservedThreadsCount;
    if (nextIndex > g_reservedThreadsMaxIndex) {
        KSLOG_ERROR("Too many reserved threads (%d). Max is %d", nextIndex,
                    g_reservedThreadsMaxIndex);
        return;
    }
    g_reservedThreads[g_reservedThreadsCount++] = thread;
}

void ksmc_suspendEnvironment() {
    // TODO
}

void ksmc_resumeEnvironment() {
    // TODO
}

int ksmc_getThreadCount(const KSMachineContext *const context) {
    // TODO
    return 0;
}

KSThread ksmc_getThreadAtIndex(const KSMachineContext *const context, int index) {
    // TODO
    return 0;

}

int ksmc_indexOfThread(const KSMachineContext *const context, KSThread thread) {
    // TODO
    return -1;
}

bool ksmc_isCrashedContext(const KSMachineContext *const context) {
    // TODO
    return false;
}

static inline bool isContextForCurrentThread(const KSMachineContext *const context) {
    // TODO
    return false;
}

static inline bool isSignalContext(const KSMachineContext *const context) {
    // TODO
    return false;
}

bool ksmc_canHaveCPUState(const KSMachineContext *const context) {
    return !isContextForCurrentThread(context) || isSignalContext(context);
}

bool ksmc_hasValidExceptionRegisters(const KSMachineContext *const context) {
    return ksmc_canHaveCPUState(context) && ksmc_isCrashedContext(context);
}
