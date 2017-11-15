#ifndef HDR_KSCrashMonitorType_h
#define HDR_KSCrashMonitorType_h

#ifdef __cplusplus
extern "C" {
#endif


/** Various aspects of the system that can be monitored:
 * - Mach kernel exception
 * - Fatal signal
 * - Uncaught C++ exception
 * - Uncaught Objective-C NSException
 * - Deadlock on the main thread
 * - User reported custom exception
 */
typedef enum
{
    /* Captures and reports Mach exceptions. */
    KSCrashMonitorTypeMachException      = 0x01,
    
    /* Captures and reports POSIX signals. */
    KSCrashMonitorTypeSignal             = 0x02,
    
    /* Captures and reports C++ exceptions.
     * Note: This will slightly slow down exception processing.
     */
    KSCrashMonitorTypeCPPException       = 0x04,
    
    /* Captures and reports NSExceptions. */
    KSCrashMonitorTypeNSException        = 0x08,
    
    /* Detects and reports a deadlock in the main thread. */
    KSCrashMonitorTypeMainThreadDeadlock = 0x10,
    
    /* Accepts and reports user-generated exceptions. */
    KSCrashMonitorTypeUserReported       = 0x20,
    
    /* Keeps track of and injects system information. */
    KSCrashMonitorTypeSystem             = 0x40,
    
    /* Keeps track of and injects application state. */
    KSCrashMonitorTypeApplicationState   = 0x80,
    
    /* Keeps track of zombies, and injects the last zombie NSException. */
    KSCrashMonitorTypeZombie             = 0x100,
} KSCrashMonitorType;

#define KSCrashMonitorTypeAll              \
(                                          \
    KSCrashMonitorTypeMachException      | \
    KSCrashMonitorTypeSignal             | \
    KSCrashMonitorTypeCPPException       | \
    KSCrashMonitorTypeNSException        | \
    KSCrashMonitorTypeMainThreadDeadlock | \
    KSCrashMonitorTypeUserReported       | \
    KSCrashMonitorTypeSystem             | \
    KSCrashMonitorTypeApplicationState   | \
    KSCrashMonitorTypeZombie               \
)

#define KSCrashMonitorTypeExperimental     \
(                                          \
    KSCrashMonitorTypeMainThreadDeadlock   \
)

#define KSCrashMonitorTypeDebuggerUnsafe   \
(                                          \
    KSCrashMonitorTypeMachException      | \
    KSCrashMonitorTypeSignal             | \
    KSCrashMonitorTypeCPPException       | \
    KSCrashMonitorTypeNSException          \
)

#define KSCrashMonitorTypeAsyncSafe        \
(                                          \
    KSCrashMonitorTypeMachException      | \
    KSCrashMonitorTypeSignal               \
)

#define KSCrashMonitorTypeOptional         \
(                                          \
    KSCrashMonitorTypeZombie               \
)
    
#define KSCrashMonitorTypeAsyncUnsafe (KSCrashMonitorTypeAll & (~KSCrashMonitorTypeAsyncSafe))

/** Monitors that are safe to enable in a debugger. */
#define KSCrashMonitorTypeDebuggerSafe (KSCrashMonitorTypeAll & (~KSCrashMonitorTypeDebuggerUnsafe))

/** Monitors that are safe to use in a production environment.
 * All other monitors should be considered experimental.
 */
#define KSCrashMonitorTypeProductionSafe (KSCrashMonitorTypeAll & (~KSCrashMonitorTypeExperimental))

/** Production safe monitors, minus the optional ones. */
#define KSCrashMonitorTypeProductionSafeMinimal (KSCrashMonitorTypeProductionSafe & (~KSCrashMonitorTypeOptional))

/** Monitors that are required for proper operation.
 * These add essential information to the reports, but do not trigger reporting.
 */
#define KSCrashMonitorTypeRequired (KSCrashMonitorTypeSystem | KSCrashMonitorTypeApplicationState)

/** Effectively disables automatica reporting. The only way to generate a report
 * in this mode is by manually calling kscrash_reportUserException().
 */
#define KSCrashMonitorTypeManual (KSCrashMonitorTypeRequired | KSCrashMonitorTypeUserReported)

#define KSCrashMonitorTypeNone 0

const char* kscrashmonitortype_name(KSCrashMonitorType monitorType);


#ifdef __cplusplus
}
#endif

#endif // HDR_KSCrashMonitorType_h
