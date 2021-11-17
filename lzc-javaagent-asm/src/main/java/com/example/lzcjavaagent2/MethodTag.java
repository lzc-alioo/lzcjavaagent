package com.example.lzcjavaagent2;

/**
 * @author: 悟心
 * @time: 2021/11/2 20:47
 * @description:
 */
public class MethodTag {
    private String fullClassName;
    private String methodName;
    private long cost;

    public MethodTag() {
    }

    public MethodTag(String fullClassName, String methodName) {
        this.fullClassName = fullClassName;
        this.methodName = methodName;
    }

    public String getFullClassName() {
        return fullClassName;
    }

    public void setFullClassName(String fullClassName) {
        this.fullClassName = fullClassName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public long getCost() {
        return cost;
    }

    public void setCost(long cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "MethodTag{" +
                "fullClassName='" + fullClassName + '\'' +
                ", methodName='" + methodName + '\'' +
                ", cost=" + cost +
                '}';
    }
}
