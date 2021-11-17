package com.example.lzcjavaagent2.track;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 * 博客：http://itstack.org
 * 论坛：http://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛获取学习源码｝
 * create by fuzhengwei on 2019
 */
public class Span {

    private String linkId;  //链路ID

    private String className;
    private String methodName;
    private int lineNumber;

    private long enterTime; //方法进入时间
    private long exitTime; //方法退出时间
    private long costTime;


    protected List<Span> children;

    public Span() {
    }

    public Span(String linkId) {
        this.linkId = linkId;
        this.enterTime = System.nanoTime();
    }

    public String getLinkId() {
        return linkId;
    }

    public void setLinkId(String linkId) {
        this.linkId = linkId;
    }

    public long getEnterTime() {
        return enterTime;
    }

    public void setEnterTime(long enterTime) {
        this.enterTime = enterTime;
    }

    public long getExitTime() {
        return exitTime;
    }

    public void setExitTime(long exitTime) {
        this.exitTime = exitTime;
    }

    public long getCostTime() {
        return costTime;
    }

    public void setCostTime(long costTime) {
        this.costTime = costTime;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    @Override
    public String toString() {
        return "Span{" +
                "linkId='" + linkId + '\'' +
                ", className='" + className + '\'' +
                ", methodName='" + methodName + '\'' +
                ", lineNumber=" + lineNumber +
                ", enterTime=" + enterTime +
                ", exitTime=" + exitTime +
                ", costTime=" + costTime +
                ", children=" + children +
                '}';
    }

    public String formatDate(Date date) {
        if (date == null) {
            return null;
        }
        return date.toInstant().atZone(ZoneId.of("+8")).toLocalDateTime().toString();
    }
}
