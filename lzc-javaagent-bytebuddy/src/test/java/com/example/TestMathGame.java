package com.example;

import com.example.lzcjavaagent3.MyAdvice;
import demo.FileUtil;
import demo.MathGame;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import org.testng.annotations.Test;

/**
 * @author: 悟心
 * @time: 2021/11/10 00:22
 * @description:
 */
public class TestMathGame {

    @Test
    public void test_byteBuddy() throws Exception {
        DynamicType.Unloaded<?> dynamicType = new ByteBuddy()
                .subclass(MathGame.class)
                .method(ElementMatchers.named("run"))
                .intercept(MethodDelegation.to(MyAdvice.class))
                .make();


        // 输出类字节码
        FileUtil.outputClazz(dynamicType.getBytes(), "/Users/mac/work/gitstudy/lzcjavaagent/lzc-javaagent-bytebuddy/target/classes/lzc/demo/HelloWorld.class");


        // 加载类
        Class<?> clazz = dynamicType.load(MathGame.class.getClassLoader())
                .getLoaded();

        // 反射调用
        clazz.getMethod("run").invoke(clazz.newInstance());
    }
}
