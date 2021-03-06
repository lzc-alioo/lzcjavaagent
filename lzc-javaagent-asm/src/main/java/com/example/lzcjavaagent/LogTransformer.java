package com.example.lzcjavaagent;


import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class LogTransformer implements ClassFileTransformer {
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        try {
            System.out.println("transform:" + className);
            if (className.startsWith("java") || className.startsWith("sun") || className.startsWith("com/intellij/rt/debugger")) {
                return classfileBuffer;
            }

            ClassReader cr = new ClassReader(className);
            ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
            TimeCountAdpter timeCountAdpter = new TimeCountAdpter(cw);
            cr.accept(timeCountAdpter, ClassReader.EXPAND_FRAMES);

            System.out.println("transform:" + className + "|success|class byte code beofore:" + classfileBuffer.length + ",after:" + cw.toByteArray().length);
            return cw.toByteArray();
        } catch (IOException e) {
            System.out.println("alioo IOException:" + className + ":" + e.getMessage());
            e.printStackTrace();
        }

        return classfileBuffer;
    }
}

