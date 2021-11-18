package com.example.lzcjavaagent2.model;

import java.util.LinkedList;
import java.util.List;

/**
 * Abstract Node of TraceCommand
 *
 * @author gongdewei 2020/4/28
 */
public class TraceNode {

    protected TraceNode parent;
    protected List<TraceNode> children;

    public TraceNode() {
    }

    public void addChild(TraceNode child) {
        if (children == null) {
            //LinkedList目前不能进行字节码注入，否则启动会报栈溢出
            //准确地说注入类，以及依赖的类均不能进行增强
            children = new LinkedList<TraceNode>();
        }
        this.children.add(child);
        child.setParent(this);
    }

    public void begin() {
    }

    public void end() {
    }


    public TraceNode parent() {
        return parent;
    }

    public void setParent(TraceNode parent) {
        this.parent = parent;
    }

    public List<TraceNode> getChildren() {
        return children;
    }
}
