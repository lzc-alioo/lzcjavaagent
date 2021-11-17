package com.example.lzcjavaagent2;

import com.sun.tools.attach.*;

import java.io.IOException;
import java.util.List;

/**
 * @author: 悟心
 * @time: 2021/11/2 11:10
 * @description:
 *  java -cp .:/Library/Java/JavaVirtualMachines/jdk1.8.0_192.jdk/Contents/Home/lib/tools.jar com.example.lzcjavaagent2.AttachAfterAppRun2
 */
public class AttachAfterAppRun2 {
    public static void main(String[] args) throws AgentLoadException, IOException, AgentInitializationException, AttachNotSupportedException, InterruptedException {

        String agentArgs = "demo.MathGame run";

        //获取当前系统中所有 运行中的 虚拟机
        System.out.println("show jvm list");
        List<VirtualMachineDescriptor> list = VirtualMachine.list();
        for (VirtualMachineDescriptor vmd : list) {
            //如果虚拟机的名称为 xxx 则 该虚拟机为目标虚拟机，获取该虚拟机的 pid
            //然后加载 agent.jar 发送给该虚拟机
            System.out.println(">>" + vmd.displayName());
            if (vmd.displayName().endsWith("demo.MathGame")) {

                System.out.println("demo.MathGame matched,pid:" + vmd.id());
                VirtualMachine virtualMachine = VirtualMachine.attach(vmd.id());
                virtualMachine.loadAgent("/Users/mac/work/gitstudy/lzcjavaagent/lzc-javaagent-asm/target/lzc-javaagent-asm-0.0.1-SNAPSHOT.jar", agentArgs);
                virtualMachine.detach();
                System.out.println("attach done");
            }
        }
//        Thread.sleep(15000L);
    }
}
