package com.example.lzcjavaagent;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

public class TraceAgent {

    public static void premain(String agentArgs, Instrumentation instrumentation) {
        instrumentation.addTransformer(new LogTransformer(), true);
    }

    public static void agentmain(String agentArgs, Instrumentation instrumentation) throws UnmodifiableClassException {
        instrumentation.addTransformer(new LogTransformer(), true);

        try {
            Class classes[] = instrumentation.getAllLoadedClasses();
            for (int i = 0; i < classes.length; i++) {
                if (classes[i].getName().equals("demo.MathGame")) {
                    System.out.println("Reloading: " + classes[i].getName());
                    instrumentation.retransformClasses(classes[i]);
                    break;
                }
            }
        } catch (UnmodifiableClassException e) {
            e.printStackTrace();
        }
        System.out.println("agentmain done");

    }
}
