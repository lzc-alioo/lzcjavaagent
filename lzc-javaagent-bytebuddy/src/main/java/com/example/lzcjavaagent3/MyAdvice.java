package com.example.lzcjavaagent3;

import com.alibaba.bytekit.asm.interceptor.annotation.AtInvoke;
import com.example.lzcjavaagent3.track.TrackContext;
import com.example.lzcjavaagent3.track.TrackManager;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import com.alibaba.bytekit.asm.binding.Binding;

import java.lang.reflect.Method;
import java.util.UUID;
import java.util.concurrent.Callable;

/**
 * 博客：http://itstack.org
 * 论坛：http://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛获取学习源码｝
 * create by fuzhengwei on 2019
 */
public class MyAdvice {


    @RuntimeType
    public static Object intercept(@Origin Method method,@SuperCall Callable<?> callable) throws Exception {
        long start = System.currentTimeMillis();
        Object resObj = null;
        try {
            int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();

            resObj = callable.call();
            return resObj;
        } finally {
            System.out.println("方法名称：" + method.getName());
            System.out.println("入参个数：" + method.getParameterCount());
//            System.out.println("入参类型：" + method.getParameterTypes()[0].getTypeName() + "、" + method.getParameterTypes()[1].getTypeName());
            System.out.println("出参类型：" + method.getReturnType().getName());
            System.out.println("出参结果：" + resObj);
            System.out.println("方法耗时：" + (System.currentTimeMillis() - start) + "ms");
        }
    }


//    @Advice.OnMethodEnter
//    public static void enter(@Origin("#t") String className, @Advice.Origin("#m") String methodName) {
//        String linkId = TrackManager.getCurrentSpan();
//        if (null == linkId) {
//            linkId = UUID.randomUUID().toString();
//            TrackContext.setLinkId(linkId);
//        }
//        String entrySpan = TrackManager.createEntrySpan();
//        System.out.println("["+Thread.currentThread().getName()+"]链路追踪：" + entrySpan + " " + className + "." + methodName);
//
//    }
//
//    @Advice.OnMethodExit()
//    public static void exit(@Origin("#t") String className, @Advice.Origin("#m") String methodName) {
//        String linkId = TrackManager.getExitSpan();
//        System.out.println("["+Thread.currentThread().getName()+"]linkId=" + linkId+",methodName="+methodName);
//    }

}
