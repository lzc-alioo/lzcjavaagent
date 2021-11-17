package com.example.lzcjavaagent2.track;

/**
 * 博客：http://itstack.org
 * 论坛：http://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛获取学习源码｝
 * create by fuzhengwei on 2019
 */
public class TrackContext {

    private static final ThreadLocal<Span> trackLocal = new ThreadLocal<>();

    public static void clear(){
        trackLocal.remove();
    }

    public static ThreadLocal<Span> getTrackLocal() {
        return trackLocal;
    }
}
