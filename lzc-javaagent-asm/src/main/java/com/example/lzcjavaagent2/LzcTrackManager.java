package com.example.lzcjavaagent2;

import com.example.lzcjavaagent2.track.*;

import java.util.UUID;

/**
 * 追踪管控
 * 博客：http://itstack.org
 * 论坛：http://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛获取学习源码｝
 * create by fuzhengwei on 2019
 */
public class LzcTrackManager {

//    private static final ThreadLocal<Stack<Span>> threadTrack = new ThreadLocal<Stack<Span>>(){
//        @Override
//        protected Stack<Span> initialValue() {
//            return  new Stack<>();
//        }
//    };

//    private static final ThreadLocal<TraceEntity> threadLocal = new ThreadLocal<TraceEntity>() ;


    private static Span createSpan() {
//        Stack<Span> stack = threadTrack.get();
//        if (stack == null) {
//            stack = new Stack<>();
//            threadTrack.set(stack);
//        }
        String linkId;
//        if (stack.isEmpty()) {
            linkId = UUID.randomUUID().toString();
//            if (linkId == null) {
//                linkId = "nvl";
//                TrackContext.setLinkId(linkId);
//            }
            return new Span(linkId);
//        } else {
//            Span span = stack.peek();
////            linkId = span.getLinkId();
////            TrackContext.setLinkId(linkId);
//           return span;
//        }

    }

//    public static TraceEntity currentEntrySpan(String className, String methodName, int lineNumber, boolean isInvoking) {
//        TraceEntity traceEntity = threadLocal.get();
//        if(traceEntity==null){
//            traceEntity = new TraceEntity(className, methodName, lineNumber, isInvoking);
//            threadLocal.set(traceEntity);
//        }
//        return traceEntity;
//    }


//    public static Span getExitSpan() {
//        Stack<Span> stack = threadTrack.get();
//        if (stack == null || stack.isEmpty()) {
//            TrackContext.clear();
//            return null;
//        }
//        return stack.pop();
//    }
//
//    public static Span getCurrentSpan() {
//        Stack<Span> stack = threadTrack.get();
//        if (stack == null || stack.isEmpty()) {
//            return null;
//        }
//        return stack.peek();
//    }


}
