package com.example.lzcjavaagent2;


import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;

public class CostTimeTransformer implements ClassFileTransformer {
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) {

//        System.out.println("transform:" + className);

        if (className.startsWith("java/lang/StringBuffer")) {
            return enhance(loader, className, classBeingRedefined, protectionDomain, classfileBuffer);
        }

        if (className.startsWith("java") || className.startsWith("sun")) {
            return classfileBuffer;
        }
        if (className.startsWith("java/util/IdentityHashMap") || className.startsWith("java/lang/Throwable")) {
            return classfileBuffer;
        }
        if (className.startsWith("sun")) {
            return classfileBuffer;
        }
        if (className.startsWith("org/objectweb/asm") || className.startsWith("com/intellij") || className.startsWith("org/jetbrains")) {
            return classfileBuffer;
        }

        if (className.startsWith("com/example/lzcjavaagent2")) {
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

