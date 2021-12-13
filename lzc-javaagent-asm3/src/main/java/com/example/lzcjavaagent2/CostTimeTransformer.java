package com.example.lzcjavaagent2;


import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;

public class CostTimeTransformer implements ClassFileTransformer {
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) {

        //白名单
        if (TraceAgent.isWhiteClass(className)) {
            return enhance(loader, className, classBeingRedefined, protectionDomain, classfileBuffer);
        }

        //黑名单
        if (TraceAgent.isBlackClass(className)) {
            return classfileBuffer;
        }

        return enhance(loader, className, classBeingRedefined, protectionDomain, classfileBuffer);
    }

    public byte[] enhance(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) {
        try {
            ClassReader cr = new ClassReader(className);
            ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
            CostTimeClassVisitor costTimeClassVisitor = new CostTimeClassVisitor(cw);
            cr.accept(costTimeClassVisitor, ClassReader.EXPAND_FRAMES);

            System.out.println("transform:" + className + "|success|class byte code beofore:" + classfileBuffer.length + ",after:" + cw.toByteArray().length);
            return cw.toByteArray();
        } catch (IOException e) {
            System.out.println("alioo IOException:" + className + ":" + e.getMessage());
            e.printStackTrace();
        } catch (Throwable e) {
            System.out.println("alioo Throwable:" + className + ":" + e.getMessage());
            e.printStackTrace();
        }

        return classfileBuffer;
    }

}

