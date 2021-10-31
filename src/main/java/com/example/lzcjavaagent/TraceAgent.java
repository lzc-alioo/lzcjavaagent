package com.example.lzcjavaagent;

import java.lang.instrument.Instrumentation;

public class TraceAgent {

    public static void premain(String agentArgs, Instrumentation instrumentation){
        instrumentation.addTransformer(new LogTransformer());
    }
}
