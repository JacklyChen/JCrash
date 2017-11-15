/**
 * Keeps watch for crashes and informs via callback when on occurs.
 */
#ifndef HDR_KSCrashMonitor_h
#define HDR_KSCrashMonitor_h

#ifdef __cplusplus
extern "C" {
#endif


#include "KSCrashMonitorType.h"
#include "KSThread.h"
    
#include <stdbool.h>

struct KSCrash_MonitorContext;


// ============================================================================
#pragma mark - External API -
// ============================================================================

/** Set which monitors are active.
 *
 * @param monitorTypes Which monitors should be active.
 */
void kscm_setActiveMonitors(KSCrashMonitorType monitorTypes);

/** Get the currently active monitors.
 */
KSCrashMonitorType kscm_getActiveMonitors(void);

/** Set the callback to call when an event is captured.
 *
 * @param onEvent Called whenever an event is captured.
 */
void kscm_setEventCallback(void (*onEvent)(struct KSCrash_MonitorContext* monitorContext));


// ============================================================================
#pragma mark - Internal API -
// ============================================================================

typedef struct
{
    void (*setEnabled)(bool isEnabled);
    bool (*isEnabled)(void);
    void (*addContextualInfoToEvent)(struct KSCrash_MonitorContext* eventContext);
} KSCrashMonitorAPI;

/** Notify that a fatal exception has been captured.
 *  This allows the system to take appropriate steps in preparation.
 *
 * @oaram isAsyncSafeEnvironment If true, only async-safe functions are allowed from now on.
 */
bool kscm_notifyFatalExceptionCaptured(bool isAsyncSafeEnvironment);

/** Start general exception processing.
 *
 * @oaram context Contextual information about the exception.
 */
void kscm_handleException(struct KSCrash_MonitorContext* context);


#ifdef __cplusplus
}
#endif

#endif // HDR_KSCrashMonitor_h
