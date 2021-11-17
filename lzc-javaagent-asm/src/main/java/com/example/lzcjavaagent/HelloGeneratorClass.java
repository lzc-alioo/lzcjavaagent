package com.example.lzcjavaagent;

import org.objectweb.asm.*;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 通过ASM生成类的字节码
 *
 * @author Administrator
 */
public class HelloGeneratorClass implements Opcodes {

    /**
     * 使用构造Hello类class字节码
     */
    public static byte[] generatorHelloClass() throws Exception {

        ClassWriter cw = new ClassWriter(0);
        FieldVisitor fv;
        MethodVisitor mv;

        cw.visit(Opcodes.V1_7, Opcodes.ACC_PUBLIC + Opcodes.ACC_SUPER, "com/lzc/Hello", null, "java/lang/Object", null);

        cw.visitSource("Hello.java", null);

        {
            fv = cw.visitField(Opcodes.ACC_PUBLIC + Opcodes.ACC_FINAL + Opcodes.ACC_STATIC, "FLAG", "Ljava/lang/String;", null, "\u6211\u662f\u5e38\u91cf");
            fv.visitEnd();
        }
        {
            mv = cw.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
            mv.visitCode();
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitVarInsn(Opcodes.ALOAD, 0);
            mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
            mv.visitInsn(Opcodes.RETURN);
            Label l1 = new Label();
            mv.visitLabel(l1);
            mv.visitMaxs(1, 1);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(Opcodes.ACC_PUBLIC, "display", "()V", null, null);
            mv.visitCode();
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitLineNumber(16, l0);
            mv.visitInsn(Opcodes.ICONST_0);
            mv.visitVarInsn(Opcodes.ISTORE, 1);
            Label l1 = new Label();
            mv.visitLabel(l1);
            mv.visitFrame(Opcodes.F_APPEND, 1, new Object[]{Opcodes.INTEGER}, 0, null);
            mv.visitVarInsn(Opcodes.ILOAD, 1);
            mv.visitIntInsn(Opcodes.BIPUSH, 6);
            Label l2 = new Label();
            mv.visitJumpInsn(Opcodes.IF_ICMPGE, l2);
            Label l3 = new Label();
            mv.visitLabel(l3);
            mv.visitLineNumber(17, l3);
            mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            mv.visitLdcInsn(">>>>>>>>>>\u6211\u662f\u5e38\u91cf");
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
            Label l4 = new Label();
            mv.visitLabel(l4);
            mv.visitLineNumber(16, l4);
            mv.visitIincInsn(1, 1);
            mv.visitJumpInsn(Opcodes.GOTO, l1);
            mv.visitLabel(l2);
            mv.visitLineNumber(20, l2);
            mv.visitFrame(Opcodes.F_CHOP, 1, null, 0, null);
            mv.visitInsn(Opcodes.RETURN);
            Label l5 = new Label();
            mv.visitLabel(l5);
            mv.visitLocalVariable("var1", "I", null, l1, l2, 1);
            mv.visitLocalVariable("this", "Ldemo/Hello;", null, l0, l5, 0);
            mv.visitMaxs(2, 2);
            mv.visitEnd();

        }
        {
            mv = cw.visitMethod(Opcodes.ACC_PUBLIC, "testList", "()Ljava/util/List;", "()Ljava/util/List<Ljava/lang/String;>;", null);
            mv.visitCode();
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitTypeInsn(Opcodes.NEW, "java/util/ArrayList");
            mv.visitInsn(Opcodes.DUP);
            mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/util/ArrayList", "<init>", "()V", false);
            mv.visitVarInsn(Opcodes.ASTORE, 1);
            Label l1 = new Label();
            mv.visitLabel(l1);
            mv.visitVarInsn(Opcodes.ALOAD, 1);
            mv.visitLdcInsn("Tome");
            mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, "java/util/List", "add", "(Ljava/lang/Object;)Z", true);
            mv.visitInsn(Opcodes.POP);
            Label l2 = new Label();
            mv.visitLabel(l2);
            mv.visitVarInsn(Opcodes.ALOAD, 1);
            mv.visitLdcInsn("Jack");
            mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, "java/util/List", "add", "(Ljava/lang/Object;)Z", true);
            mv.visitInsn(Opcodes.POP);
            Label l3 = new Label();
            mv.visitLabel(l3);
            mv.visitVarInsn(Opcodes.ALOAD, 1);
            mv.visitLdcInsn("Lily");
            mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, "java/util/List", "add", "(Ljava/lang/Object;)Z", true);
            mv.visitInsn(Opcodes.POP);
            Label l4 = new Label();
            mv.visitLabel(l4);
            mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            mv.visitTypeInsn(Opcodes.NEW, "java/lang/StringBuilder");
            mv.visitInsn(Opcodes.DUP);
            mv.visitLdcInsn(">>>>>>>>>>testList > list.size = ");
            mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "(Ljava/lang/String;)V", false);
            mv.visitVarInsn(Opcodes.ALOAD, 1);
            mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, "java/util/List", "size", "()I", true);
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(I)Ljava/lang/StringBuilder;",
                    false);
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
            Label l5 = new Label();
            mv.visitLabel(l5);
            mv.visitVarInsn(Opcodes.ALOAD, 1);
            mv.visitInsn(Opcodes.ARETURN);
            Label l6 = new Label();
            mv.visitLabel(l6);
            mv.visitMaxs(4, 2);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(Opcodes.ACC_PUBLIC, "testMap", "(Ljava/util/List;)Ljava/util/Map;", "(Ljava/util/List<Ljava/lang/String;>;)Ljava/util/Map<Ljava/lang/String;Ljava/lang/String;>;", null);
            mv.visitCode();
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitLineNumber(32, l0);
            mv.visitTypeInsn(Opcodes.NEW, "java/util/HashMap");
            mv.visitInsn(Opcodes.DUP);
            mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/util/HashMap", "<init>", "()V", false);
            mv.visitVarInsn(Opcodes.ASTORE, 2);
            Label l1 = new Label();
            mv.visitLabel(l1);
            mv.visitLineNumber(34, l1);
            mv.visitInsn(Opcodes.ICONST_0);
            mv.visitVarInsn(Opcodes.ISTORE, 3);
            Label l2 = new Label();
            mv.visitLabel(l2);
            mv.visitFrame(Opcodes.F_APPEND, 2, new Object[]{"java/util/Map", Opcodes.INTEGER}, 0, null);
            mv.visitVarInsn(Opcodes.ILOAD, 3);
            mv.visitVarInsn(Opcodes.ALOAD, 1);
            mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, "java/util/List", "size", "()I", true);
            Label l3 = new Label();
            mv.visitJumpInsn(Opcodes.IF_ICMPGE, l3);
            Label l4 = new Label();
            mv.visitLabel(l4);
            mv.visitLineNumber(35, l4);
            mv.visitVarInsn(Opcodes.ALOAD, 1);
            mv.visitVarInsn(Opcodes.ILOAD, 3);
            mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, "java/util/List", "get", "(I)Ljava/lang/Object;", true);
            mv.visitTypeInsn(Opcodes.CHECKCAST, "java/lang/String");
            mv.visitVarInsn(Opcodes.ASTORE, 4);
            Label l5 = new Label();
            mv.visitLabel(l5);
            mv.visitLineNumber(36, l5);
            mv.visitVarInsn(Opcodes.ALOAD, 2);
            mv.visitVarInsn(Opcodes.ALOAD, 4);
            mv.visitTypeInsn(Opcodes.NEW, "java/lang/StringBuilder");
            mv.visitInsn(Opcodes.DUP);
            mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);
            mv.visitVarInsn(Opcodes.ALOAD, 4);
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
            mv.visitVarInsn(Opcodes.ALOAD, 4);
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
            mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, "java/util/Map", "put", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", true);
            mv.visitInsn(Opcodes.POP);
            Label l6 = new Label();
            mv.visitLabel(l6);
            mv.visitLineNumber(34, l6);
            mv.visitIincInsn(3, 1);
            mv.visitJumpInsn(Opcodes.GOTO, l2);
            mv.visitLabel(l3);
            mv.visitLineNumber(39, l3);
            mv.visitFrame(Opcodes.F_CHOP, 1, null, 0, null);
            mv.visitVarInsn(Opcodes.ALOAD, 2);
            mv.visitInsn(Opcodes.ARETURN);
            Label l7 = new Label();
            mv.visitLabel(l7);
            mv.visitLocalVariable("str", "Ljava/lang/String;", null, l5, l6, 4);
            mv.visitLocalVariable("i", "I", null, l2, l3, 3);
            mv.visitLocalVariable("this", "Ldemo/Hello;", null, l0, l7, 0);
            mv.visitLocalVariable("list", "Ljava/util/List;", "Ljava/util/List<Ljava/lang/String;>;", l0, l7, 1);
            mv.visitLocalVariable("map", "Ljava/util/Map;", "Ljava/util/Map<Ljava/lang/String;Ljava/lang/String;>;", l1, l7, 2);
            mv.visitMaxs(4, 5);
            mv.visitEnd();
        }
        cw.visitEnd();

        return cw.toByteArray();
    }

    /**
     * 生成保存class文件
     */
    public static byte[] createClass() {
        try {
            byte[] data = generatorHelloClass();
//            File file = new File("/Users/mac/Download/classes/com/lzc/Hello.class");
            File file = new File("/Users/mac/work/gitstudy/lzcjavaagent/target/classes/com/lzc/Hello.class");
            File parent1 = new File(file.getParent());
            boolean mkdirs = parent1.mkdirs();
            System.out.println("mkdirs=" + mkdirs + ",exist=" + file.exists());
            boolean createNewFile = file.createNewFile();
            System.out.println("createNewFile=" + createNewFile + ",exist=" + file.exists());

            FileOutputStream fos = new FileOutputStream(file);
            fos.write(data);
            fos.close();

            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 基于反射生成的class文件生成java对象
     */
    public static void useClass(byte[] data) {
        try {
            MyClassLoader myClassLoader = new MyClassLoader();
            Class<?> helloClass = myClassLoader.defineClass("com.lzc.Hello", data);
            Object obj = helloClass.newInstance();
            Method method = helloClass.getMethod("display", null);
            method.invoke(obj, null);

            method = helloClass.getMethod("testList", null);
            Object result = method.invoke(obj, null);
            System.out.println(result);

            method = helloClass.getMethod("testMap", List.class);
            Object result2 = method.invoke(obj, result);
            System.out.println(result2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static class MyClassLoader extends ClassLoader {


        public MyClassLoader() {
            super();
        }

        @Override
        public Class<?> loadClass(String name) throws ClassNotFoundException {
            return super.loadClass(name);
        }

        public Class<?> defineClass(String name, byte[] b) {
            return super.defineClass(name, b, 0, b.length);
        }

    }


    /**
     * AOP测试
     *
     * @param args
     * @author SHANHY
     * @create 2016年2月3日
     */
    public static void main(String[] args) {
        try {
            byte[] data = createClass();

            useClass(data);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}