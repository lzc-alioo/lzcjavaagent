package com.example.lzcjavaagent2.aspect;

import com.example.lzcjavaagent2.model.MethodNode;
import com.example.lzcjavaagent2.model.TraceEntity;
import com.example.lzcjavaagent2.model.TraceNode;

/**
 * @author: 悟心
 * @time: 2021/11/2 20:47
 * @description:
 */
public class ProfilingAspect {

    public static String traceClassName;
    public static String traceMethodName;
    private static final ThreadLocal<TraceEntity> threadLocal = new ThreadLocal<TraceEntity>();

    public static void methodEnter(String className, String methodName) {

        int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();

        TraceEntity traceEntity = currentEntrySpan(className, methodName, lineNumber);

        traceEntity.tree.begin(className, methodName, -1);
        traceEntity.deep++;
    }

    private static boolean matchClassAndMethod(String className, String methodName) {
        if (className.equals(traceClassName) && traceMethodName.equals(methodName)) {
            return true;
        }
        return false;
    }


    public static void methodExit(String className, String methodName) {
        int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();

        TraceEntity traceEntity = currentEntrySpan(className, methodName, lineNumber);
        traceEntity.tree.end();

        if (traceEntity.deep >= 1) {
            traceEntity.deep--;
        }
        if (traceEntity.deep == 0) {
            threadLocal.remove();

            MethodNode root = (MethodNode) (traceEntity.tree.root);
            if (!matchClassAndMethod(root.getClassName(), root.getMethodName())) {
                return;
            }
            log(root, 1);
        }
    }

    private static void log(TraceNode root, int idx) {
        String space = "----";
        MethodNode tmpNode = (MethodNode) root;
        if (tmpNode == null) {
            return;
        }
        String tmp = "";
        for (int i = 0; i < idx; i++) {
            tmp += space;
        }

        print(tmp, tmpNode);
        if (tmpNode.getChildren() != null && tmpNode.getChildren().size() > 0) {
            idx++;
            for (TraceNode tmpNode2 : tmpNode.getChildren()) {
                log(tmpNode2, idx);
            }
        }

    }


    private static void print(String tmp, MethodNode tmpNode) {
        System.out.println(tmp + "[" + tmpNode.getCost() / 1000.0 + "ms] " + tmpNode.getClassName() + ":" + tmpNode.getMethodName() + "() #" + tmpNode.getLineNumber());
    }

    public static TraceEntity currentEntrySpan(String className, String methodName, int lineNumber) {
        TraceEntity traceEntity = threadLocal.get();
        if (traceEntity == null) {
            traceEntity = new TraceEntity(className, methodName, lineNumber);
            threadLocal.set(traceEntity);
        }
        return traceEntity;
    }
}
