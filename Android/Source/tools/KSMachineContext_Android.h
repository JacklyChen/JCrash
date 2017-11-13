#ifndef HDR_KSMachineContext_Android_h
#define HDR_KSMachineContext_Android_h

#ifdef __cplusplus
extern "C" {
    #endif

    #include <pthread.h>
    #include <stdbool.h>
    #include <sys/ucontext.h>


    typedef struct KSMachineContext {
        pthread_t thisThread;
        pthread_t allThreads[100];
        int threadCount;
        bool isCrashedContext;
        bool isCurrentThread;
        bool isStackOverflow;
        bool isSignalContext;
    //    STRUCT_MCONTEXT_L machineContext;
    } KSMachineContext;


    #ifdef __cplusplus
}
#endif

#endif // HDR_KSMachineContext_Android_h
