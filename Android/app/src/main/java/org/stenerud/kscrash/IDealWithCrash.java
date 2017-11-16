package org.stenerud.kscrash;


/**
 * 用户自定义处理crash的接口
 */
public interface IDealWithCrash{

    /**
     * 处理crash日志的回调接口
     * @param summary 异常的精简信息
     * @param detail  异常的详细信息
     */
    void dealWithJavaCrash(Throwable summary, String detail);
}

