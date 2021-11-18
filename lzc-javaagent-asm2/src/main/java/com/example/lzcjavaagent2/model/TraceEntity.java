package com.example.lzcjavaagent2.model;

/**
 * 用于在ThreadLocal中传递的实体
 *
 * @author ralf0131 2017-01-05 14:05.
 */
public class TraceEntity {

    public TraceTree tree;
    public int deep;

    public TraceEntity(String className, String methodName, int lineNumber) {
        this.tree = createTraceTree(className, methodName, lineNumber);
        this.deep = 0;
    }

    private TraceTree createTraceTree(String className, String methodName, int lineNumber) {

        MethodNode node = new MethodNode(className, methodName, lineNumber);
        TraceTree tree = new TraceTree(node);

        return tree;
    }


}
