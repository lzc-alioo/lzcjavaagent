package com.example.lzcjavaagent2;


import org.objectweb.asm.*;
import org.objectweb.asm.commons.AnalyzerAdapter;
import org.objectweb.asm.commons.LocalVariablesSorter;
import org.objectweb.asm.util.TraceClassVisitor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class CostTimeClassVisitor extends ClassVisitor implements Opcodes {
    private String className;
    private boolean isInterface;

    private String methodName;


    public CostTimeClassVisitor(ClassVisitor classVisitor) {
        super(Opcodes.ASM6, classVisitor);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
        className = name.replaceAll("/", ".");
        isInterface = (access & Opcodes.ACC_INTERFACE) != 0;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        MethodVisitor mv = cv.visitMethod(access, name, descriptor, signature, exceptions);

        if (!isInterface && mv != null && !name.equals("<init>") && !name.equals("<cinit>") && !name.startsWith("java")) {
            methodName = name;

            CostTimeMethodVisitor ca = new CostTimeMethodVisitor(mv);
            AnalyzerAdapter aa = new AnalyzerAdapter(className, access, name, descriptor, ca);
            LocalVariablesSorter lvs = new LocalVariablesSorter(access, descriptor, aa);

            ca.aa = aa;
            ca.lvs = lvs;

            return ca.lvs;

        }

        return mv;
    }


    class CostTimeMethodVisitor extends MethodVisitor {
        private int time;
        private int maxStack;
        public LocalVariablesSorter lvs;
        public AnalyzerAdapter aa;

        public CostTimeMethodVisitor(MethodVisitor methodVisitor) {
            super(Opcodes.ASM6, methodVisitor);

        }


        @Override
        public void visitCode() {
            mv.visitCode();


            mv.visitLdcInsn(className);
            mv.visitLdcInsn(methodName);
            mv.visitMethodInsn(Opcodes.INVOKESTATIC, "com/example/lzcjavaagent2/aspect/ProfilingAspect", "methodEnter", "(Ljava/lang/String;Ljava/lang/String;)V", false);

//            maxStack = 4;
        }

        @Override
        public void visitInsn(int opcode) {
            if ((opcode >= Opcodes.IRETURN && opcode <= Opcodes.RETURN) || opcode == Opcodes.ATHROW) {

                mv.visitLdcInsn(className);
                mv.visitLdcInsn(methodName);
                mv.visitMethodInsn(Opcodes.INVOKESTATIC, "com/example/lzcjavaagent2/aspect/ProfilingAspect", "methodExit", "(Ljava/lang/String;Ljava/lang/String;)V", false);

                maxStack = Math.max(aa.stack.size() + 4, maxStack);
            }
            mv.visitInsn(opcode);
        }

        @Override
        public void visitMaxs(int maxStack, int maxLocals) {
            super.visitMaxs(Math.max(maxStack, this.maxStack), maxLocals);
        }
    }


    public static void main(String[] args) {
        try {
            ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
            TraceClassVisitor traceClassVisitor = new TraceClassVisitor(classWriter, new PrintWriter(System.out));

            CostTimeClassVisitor timeCountAdpter = new CostTimeClassVisitor(traceClassVisitor);

            ClassReader classReader = new ClassReader("demo.MathGame");
            classReader.accept(timeCountAdpter, ClassReader.EXPAND_FRAMES);

//            File file = new File("/Users/mac/Download/classes/demo/Hello.class");
            File file = new File("/Users/mac/work/gitstudy/lzcjavaagent/lzc-javaagent-asm/target/classes/lzc/demo/MathGame.class");
            String parent = file.getParent();
            File parent1 = new File(parent);
            boolean mkdirs = parent1.mkdirs();
            System.out.println("mkdirs=" + mkdirs + ",exist=" + file.exists());
            boolean createNewFile = file.createNewFile();
            System.out.println("mkdirs=" + mkdirs + ",createNewFile=" + createNewFile + ",exist=" + file.exists());
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(classWriter.toByteArray());
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
