package com.example.lzcjavaagent2;

import com.example.lzcjavaagent2.aspect.ProfilingAspect;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarFile;

public class TraceAgent {

    public static void premain(String agentArgs, Instrumentation instrumentation) throws UnsupportedEncodingException {
        agentmain(agentArgs, instrumentation);
    }

    public static void agentmain(String agentArgs, Instrumentation instrumentation) throws UnsupportedEncodingException {
        try {
            instrumentation.appendToBootstrapClassLoaderSearch(new JarFile("/Users/mac/work/gitstudy/lzcjavaagent/lzc-javaagent-asm2/target/lzc-javaagent-asm2-0.0.1-SNAPSHOT.jar"));
        } catch (IOException e) {
            System.out.println("appendToSystemClassLoaderSearch这里出异常了..." + e.getMessage());
            e.printStackTrace();
        }

        instrumentation.addTransformer(new CostTimeTransformer(), true);

        agentArgs = URLDecoder.decode(agentArgs, "UTF-8");
        String traceClassName = agentArgs.split("\\s")[0];
        String traceMethodName = agentArgs.split("\\s")[1];
        ProfilingAspect.traceClassName = traceClassName;
        ProfilingAspect.traceMethodName = traceMethodName;

        Class classes[] = getMatchedClass(instrumentation);
        for (int i = 0; i < classes.length; i++) {
            try {
                System.out.println("retransformClasses:::" + classes[i]);
                instrumentation.retransformClasses(classes[i]);
            } catch (UnmodifiableClassException e) {
                System.out.println("retransformClasses这里出异常了" + classes[i] + "..." + e.getMessage());
            }
        }
        System.out.println("agentmain done");
    }

    private static Class[] getMatchedClass(Instrumentation instrumentation) {
        Class classes[] = instrumentation.getAllLoadedClasses();

        int i = 0;
        try {
            List<Class> list = new ArrayList<>();

            for (i = 0; i < classes.length; i++) {
                String className = classes[i].getName();

                if (isWhiteClass(className)) {
                    list.add(classes[i]);
                }

                if (isBlackClass(className)) {
                    continue;
                }

                list.add(classes[i]);

            }
            return list.toArray(new Class[list.size()]);

        } catch (Exception e) {
            System.out.println("getMatchedClass这里出异常了" + classes[i] + "..." + e.getMessage());
            e.printStackTrace();
        }

        return new Class[0];
    }

    public static boolean isWhiteClass(String className) {
        className = className.replace('/', '.');

        if (className.startsWith("java.lang.StringBuffer")) {
            return true;
        }

        if (className.equals("java.util.ArrayList")) {
            return true;
        }

        return false;
    }

    public static boolean isBlackClass(String className) {
        className = className.replace('/', '.');

        if (className.startsWith("java") || className.startsWith("sun")) {
            return true;
        }
        //[ [L [[L
        if (className.startsWith("[") || className.startsWith("sun")) {
            return true;
        }
        if (className.startsWith("org.objectweb.asm") || className.startsWith("com.intellij") || className.startsWith("org.jetbrains")) {
            return true;
        }

        if (className.startsWith("com.example.lzcjavaagent2")) {
            return true;
        }

        return false;
    }
}
