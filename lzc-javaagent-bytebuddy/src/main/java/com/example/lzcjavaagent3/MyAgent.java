package com.example.lzcjavaagent3;

import demo.FileUtil;
import demo.Hi;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;

import java.lang.instrument.Instrumentation;
import java.lang.reflect.Modifier;

/**
 * javaagent
 * 博客：http://itstack.org
 * 论坛：http://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛获取学习源码｝
 * create by fuzhengwei on 2019
 */
public class MyAgent {

    //JVM 首先尝试在代理类上调用以下方法
    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("基于javaagent链路追踪 agentArgs:::" + agentArgs);

//        AgentBuilder agentBuilder = new AgentBuilder.Default();
//
//
//        AgentBuilder.Transformer transformer = (builder, typeDescription, classLoader, javaModule) -> {
//            builder = builder.visit(
//                    Advice.to(MyAdvice.class)
//                            .on(ElementMatchers.isMethod()
//                                    .and(ElementMatchers.nameStartsWith("run"))
//                                    .and(ElementMatchers.not(ElementMatchers.nameStartsWith("main")))));
//            return builder;
//        };
//
//        agentBuilder = agentBuilder.type(ElementMatchers.nameStartsWith("org.itstack.demo.test")).transform(transformer).asDecorator();
//
//        //监听
//        AgentBuilder.Listener listener = new AgentBuilder.Listener() {
//            @Override
//            public void onDiscovery(String s, ClassLoader classLoader, JavaModule javaModule, boolean b) {
//
//            }
//
//            @Override
//            public void onTransformation(TypeDescription typeDescription, ClassLoader classLoader, JavaModule javaModule, boolean b, DynamicType dynamicType) {
//                System.out.println("onTransformation：" + typeDescription);
//            }
//
//            @Override
//            public void onIgnored(TypeDescription typeDescription, ClassLoader classLoader, JavaModule javaModule, boolean b) {
//                System.out.println("onIgnored：" + typeDescription);
//
//            }
//
//            @Override
//            public void onError(String s, ClassLoader classLoader, JavaModule javaModule, boolean b, Throwable throwable) {
//                System.out.println("onError：" + javaModule);
//
//            }
//
//            @Override
//            public void onComplete(String s, ClassLoader classLoader, JavaModule javaModule, boolean b) {
//                System.out.println("onComplete：" + javaModule);
//
//            }
//
//        };
//
//        agentBuilder.with(listener).installOn(inst);


        AgentBuilder.Transformer transformer = new AgentBuilder.Transformer() {
            @Override
            public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder,
                                                    TypeDescription typeDescription,
                                                    ClassLoader classLoader,
                                                    JavaModule javaModule) {
//                return builder.visit(Advice.to(MyAdvice.class).on(ElementMatchers.<MethodDescription>any()));
                return builder.method(ElementMatchers.<MethodDescription>any()) // 拦截任意方法
                        .intercept(MethodDelegation.to(MyAdvice.class)); // 委托
            }


        };

        AgentBuilder.Listener listener = new AgentBuilder.Listener() {
            @Override
            public void onDiscovery(String s, ClassLoader classLoader, JavaModule javaModule, boolean b) {
                System.out.println("onDiscovery s:" + s + " javaModule:" + javaModule);
            }

            @Override
            public void onTransformation(TypeDescription typeDescription, ClassLoader classLoader, JavaModule javaModule, boolean b, DynamicType dynamicType) {
                System.out.println("onTransformation typeDescription:" + typeDescription + " javaModule:" + javaModule);
            }

            @Override
            public void onIgnored(TypeDescription typeDescription, ClassLoader classLoader, JavaModule javaModule, boolean b) {
                System.out.println("onTransformation typeDescription:" + typeDescription + " javaModule:" + javaModule);
            }

            @Override
            public void onError(String s, ClassLoader classLoader, JavaModule javaModule, boolean b, Throwable throwable) {
                System.out.println("onError s:" + s + " javaModule:" + javaModule);
            }

            @Override
            public void onComplete(String s, ClassLoader classLoader, JavaModule javaModule, boolean b) {
                System.out.println("onComplete s:" + s + " javaModule:" + javaModule);
            }

        };

        new AgentBuilder
                .Default()
                // 指定需要拦截的类
                .type(ElementMatchers.nameStartsWith("demo"))
                .transform(transformer)
                .asDecorator()
                .with(listener)
                .installOn(inst);

    }

    public static void agentmain(String agentArgs, Instrumentation instrumentation) {
        premain(agentArgs, instrumentation);
    }


    public static void main(String[] args) {
        DynamicType.Unloaded<?> dynamicType = new ByteBuddy()
                .subclass(Object.class)
                .name("lzc.demo.HelloWorld")
                .defineMethod("main", void.class, Modifier.PUBLIC + Modifier.STATIC)
                .withParameter(String[].class, "args")
//                .intercept(FixedValue.value("Hello World!"))
                .intercept(MethodDelegation.to(Hi.class))
                .make();

// 输出类字节码
        FileUtil.outputClazz(dynamicType.getBytes(), "/Users/mac/work/gitstudy/lzcjavaagent/lzc-javaagent-bytebuddy/target/classes/lzc/demo/HelloWorld.class");
    }
}
