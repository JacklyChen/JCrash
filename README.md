# JCrash
#### Android自定义异常处理模块（Based on KSCrash 2017-11-14）  
  
#### JCrash  
Handles errors that can only be caught at the mach level, such as stack overflow.  
Tracks the REAL cause of an uncaught C++ exception.  
Handles a crash in the crash handler itself (or in the user crash handler callback).  
Extracts information about objects referenced by an exception (such as "unrecognized selector sent to instance 0xa26d9a0")  
Its pluggable server reporting architecture makes it easy to adapt to any API service.  
Dumps the stack contents.  
Diagnoses crash causes (Crash Doctor).  
Supports including extra data that the programmer supplies (before and during a crash).  
  
#### JCrash handles the following kinds of crashes:
Fatal signals  
C++ exceptions  
Java exceptions  
Main thread deadlock (experimental)  
Custom crashes (e.g. from Java languages)  

With zombie tracking enabled, JCrash will also detect a lost NSException and
print its contents. Certain kinds of memory corruption or stack corruption
crashes can cause the exception to deallocate early, further twarting efforts
to debug your app, so this feature can be quite handy at times.

Trade off: Zombie tracking at the cost of adding very slight overhead to object
           deallocation, and having some memory reserved.


#### Deadlock Detection (KSCrashMonitorTypeMainThreadDeadlock in KSCrashMonitorType.h)

**WARNING WARNING WARNING WARNING WARNING WARNING WARNING**

**This feature is UNSTABLE! It can false-positive and crash your app!**

If your main thread deadlocks, your user interface will become unresponsive,
and the user will have to manually shut down the app (for which there will be
no crash report). With deadlock detection enabled, a watchdog timer is set up.
If anything holds the main thread for longer than the watchdog timer duration,
JCrash will shut down the app and give you a stack trace showing what the
main thread was doing at the time.

#### This is wonderful, but you must be careful: 
App initialization generally occurs on the main thread. If your initialization 
code takes longer than the watchdog timer, your app will be forcibly shut down
during start up! If you enable this feature, you MUST ensure that NONE of your 
normally running code holds the main thread for longer than the watchdog value! 
At the same time,you'll want to set the timer to a low enough value that the user 
doesn't become impatient and shut down the app manually before the watchdog triggers!

Trade off: Deadlock detection, but you must be a lot more careful about what
           runs on the main thread!


#### Memory Introspection (introspectMemory in KSCrash.h)

When an app crashes, there are usually objects and strings in memory that are
being referenced by the stack, registers, or even exception messages. When
enabled, JCrash will introspect these memory regions and store their contents
in the crash report.

You can also specify a list of classes that should not be introspected by
setting the **doNotIntrospectClasses** property in JCrash.


#### Custom crash handling code (onCrash in KSCrash.h)

If you want to do some extra processing after a crash occurs (perhaps to add
more contextual data to the report), you can do so.

However, you must ensure that you only use async-safe code! There are many cases where you
can get away with doing so anyway, but there are certain classes of crashes
where handler code that disregards this warning will cause the crash handler
to crash! Note that if this happens, JCrash will detect it and write a full
report anyway, though your custom handler code may not fully run.

Trade off: Custom crash handling code, but you must be careful what you put in it!


#### JCrash log redirection

This takes whatever JCrash would have printed to the console, and writes it
to a file instead. I mostly use this for debugging JCrash itself, but it could
be useful for other purposes, so I've exposed an API for it.  



#### 具体使用
        try {  //在处理native异常时可能会跑IOException
            //日志本地处理
            KSCrashInstallationLocal.INSTANCE.install(MainActivity.this);
            //统计SDK拿到后进行封装符合格式的数据并进行存储
            KSCrashInstallationLocal.INSTANCE.setIDealWithCrash(new IDealWithCrash(){  //属于耗时操作
                @Override
                public void dealWithCrash(Throwable summary, String detail) {
                    Log.e(TAG, "dealWithCrash summary----------" + summary.toString());
                    Log.e(TAG, "dealWithCrash detail----------" + detail);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
