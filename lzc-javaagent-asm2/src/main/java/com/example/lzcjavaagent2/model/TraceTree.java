package com.example.lzcjavaagent2.model;


import java.util.List;

/**
 * Tree model of TraceCommand
 *
 * @author gongdewei 2020/4/28
 */
public class TraceTree {
    public TraceNode root;

    public TraceNode current;
    private int nodeCount = 0;

    public TraceTree(TraceNode root) {
        this.root = root;
        this.current = root;
    }

    /**
     * Begin a new method call
     *
     * @param className  className of method
     * @param methodName method name of the call
     * @param lineNumber line number of invoke point
     * @param isInvoking Whether to invoke this method in other classes
     */
    public void begin(String className, String methodName, int lineNumber, boolean isInvoking) {
        TraceNode child = findChild(current, className, methodName, lineNumber);
        if (child == null) {
            child = new MethodNode(className, methodName, lineNumber, isInvoking);
            current.addChild(child);
        }
        child.begin();
        current = child;
        nodeCount += 1;
    }

    private TraceNode findChild(TraceNode node, String className, String methodName, int lineNumber) {
        if (nodeCount == 0) {
            return node;
        }
        List<TraceNode> childList = node.getChildren();
        if (childList != null) {
            //less memory than foreach/iterator
            for (TraceNode child : childList) {
                if (matchNode(child, className, methodName, lineNumber)) {
                    return child;
                }
            }
            return null;
        }
        return null;
    }

    private boolean matchNode(TraceNode node, String className, String methodName, int lineNumber) {
        if (node instanceof MethodNode) {
            MethodNode methodNode = (MethodNode) node;
            if (lineNumber != methodNode.getLineNumber()) {
                return false;
            }
            if (className != null ? !className.equals(methodNode.getClassName()) : methodNode.getClassName() != null) {
                return false;
            }

            return methodName != null ? methodName.equals(methodNode.getMethodName()) : methodNode.getMethodName() == null;
        }
        return false;
    }

    public void end() {
        current.end();
        if (current.parent() != null) {
            current = current.parent();
        }
    }

    public void end(boolean isThrow) {
        if (isThrow) {
            if (current instanceof MethodNode) {
                MethodNode methodNode = (MethodNode) current;
                methodNode.setThrow(true);
            }
        }
        this.end();
    }

    public TraceNode getRoot() {
        return root;
    }

    public TraceNode current() {
        return current;
    }

    public int getNodeCount() {
        return nodeCount;
    }
}
