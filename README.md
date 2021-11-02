JavaAgent的那点秘密

# 前言

大家都知道Java文件编译成Class文件之后才能在Jvm中运行的，而Class文件自有一套规范:

- 不必一定是Java文件经由javac编译产生；
- 其它编程语言也可以直接生成Class文件交由Jvm运行；

既然其它语言都可以生成Class文件，理论上Java语言自己是不是也可以修改/篡改Class文件呢？答案是肯定的。那么是否有这方面的需求呢（用Java语法去直接修改Class文件） 我举个例子：

- 需要去增强别人的二方包，需要针对其中大量的方法进行增强
- 程序运行期间，动态去修改某个类（有点类似于ide中的热编译热部署）从而达到线上快速调试的目的公司级别基础框架，应用层毫不感知地进行慢sql监控，traceid链路跟踪

# ASM

目前市面上还有很多字节码技术基本上都是这么做的，而他们都是利用的jdk5官方提供JavaAgent技术 字节码技术
直接操纵字节码技术有目前已经有很多工具了，比如asm，javaassist，arthas，这里的示例选用的是asm来进行举例讲解

asm maven坐标：

```
<dependency>
    <groupId>org.ow2.asm</groupId>
    <artifactId>asm-all</artifactId>
    <version>6.0_BETA</version>
</dependency>
```

## 基于asm生成一个class文件

这个示例中，可以学到：

- 演示了生成类变量的生成
- for循环的编写方式
- 组装集合对象ArrayList，并作为出参返回
- 集合对象ArrayList作为入参进行遍历

```
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

        cw.visit(V1_7, ACC_PUBLIC + ACC_SUPER, "com/lzc/Hello", null, "java/lang/Object", null);

        cw.visitSource("Hello.java", null);

        {
            fv = cw.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "FLAG", "Ljava/lang/String;", null, "\u6211\u662f\u5e38\u91cf");
            fv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
            mv.visitCode();
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
            mv.visitInsn(RETURN);
            Label l1 = new Label();
            mv.visitLabel(l1);
            mv.visitMaxs(1, 1);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC, "display", "()V", null, null);
            mv.visitCode();
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitLineNumber(16, l0);
            mv.visitInsn(ICONST_0);
            mv.visitVarInsn(ISTORE, 1);
            Label l1 = new Label();
            mv.visitLabel(l1);
            mv.visitFrame(Opcodes.F_APPEND, 1, new Object[]{Opcodes.INTEGER}, 0, null);
            mv.visitVarInsn(ILOAD, 1);
            mv.visitIntInsn(BIPUSH, 6);
            Label l2 = new Label();
            mv.visitJumpInsn(IF_ICMPGE, l2);
            Label l3 = new Label();
            mv.visitLabel(l3);
            mv.visitLineNumber(17, l3);
            mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            mv.visitLdcInsn(">>>>>>>>>>\u6211\u662f\u5e38\u91cf");
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
            Label l4 = new Label();
            mv.visitLabel(l4);
            mv.visitLineNumber(16, l4);
            mv.visitIincInsn(1, 1);
            mv.visitJumpInsn(GOTO, l1);
            mv.visitLabel(l2);
            mv.visitLineNumber(20, l2);
            mv.visitFrame(Opcodes.F_CHOP, 1, null, 0, null);
            mv.visitInsn(RETURN);
            Label l5 = new Label();
            mv.visitLabel(l5);
            mv.visitLocalVariable("var1", "I", null, l1, l2, 1);
            mv.visitLocalVariable("this", "Ldemo/Hello;", null, l0, l5, 0);
            mv.visitMaxs(2, 2);
            mv.visitEnd();

        }
        {
            mv = cw.visitMethod(ACC_PUBLIC, "testList", "()Ljava/util/List;", "()Ljava/util/List<Ljava/lang/String;>;", null);
            mv.visitCode();
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitTypeInsn(NEW, "java/util/ArrayList");
            mv.visitInsn(DUP);
            mv.visitMethodInsn(INVOKESPECIAL, "java/util/ArrayList", "<init>", "()V", false);
            mv.visitVarInsn(ASTORE, 1);
            Label l1 = new Label();
            mv.visitLabel(l1);
            mv.visitVarInsn(ALOAD, 1);
            mv.visitLdcInsn("Tome");
            mv.visitMethodInsn(INVOKEINTERFACE, "java/util/List", "add", "(Ljava/lang/Object;)Z", true);
            mv.visitInsn(POP);
            Label l2 = new Label();
            mv.visitLabel(l2);
            mv.visitVarInsn(ALOAD, 1);
            mv.visitLdcInsn("Jack");
            mv.visitMethodInsn(INVOKEINTERFACE, "java/util/List", "add", "(Ljava/lang/Object;)Z", true);
            mv.visitInsn(POP);
            Label l3 = new Label();
            mv.visitLabel(l3);
            mv.visitVarInsn(ALOAD, 1);
            mv.visitLdcInsn("Lily");
            mv.visitMethodInsn(INVOKEINTERFACE, "java/util/List", "add", "(Ljava/lang/Object;)Z", true);
            mv.visitInsn(POP);
            Label l4 = new Label();
            mv.visitLabel(l4);
            mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            mv.visitTypeInsn(NEW, "java/lang/StringBuilder");
            mv.visitInsn(DUP);
            mv.visitLdcInsn(">>>>>>>>>>testList > list.size = ");
            mv.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "(Ljava/lang/String;)V", false);
            mv.visitVarInsn(ALOAD, 1);
            mv.visitMethodInsn(INVOKEINTERFACE, "java/util/List", "size", "()I", true);
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(I)Ljava/lang/StringBuilder;",
                    false);
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
            Label l5 = new Label();
            mv.visitLabel(l5);
            mv.visitVarInsn(ALOAD, 1);
            mv.visitInsn(ARETURN);
            Label l6 = new Label();
            mv.visitLabel(l6);
            mv.visitMaxs(4, 2);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC, "testMap", "(Ljava/util/List;)Ljava/util/Map;", "(Ljava/util/List<Ljava/lang/String;>;)Ljava/util/Map<Ljava/lang/String;Ljava/lang/String;>;", null);
            mv.visitCode();
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitLineNumber(32, l0);
            mv.visitTypeInsn(NEW, "java/util/HashMap");
            mv.visitInsn(DUP);
            mv.visitMethodInsn(INVOKESPECIAL, "java/util/HashMap", "<init>", "()V", false);
            mv.visitVarInsn(ASTORE, 2);
            Label l1 = new Label();
            mv.visitLabel(l1);
            mv.visitLineNumber(34, l1);
            mv.visitInsn(ICONST_0);
            mv.visitVarInsn(ISTORE, 3);
            Label l2 = new Label();
            mv.visitLabel(l2);
            mv.visitFrame(Opcodes.F_APPEND, 2, new Object[]{"java/util/Map", Opcodes.INTEGER}, 0, null);
            mv.visitVarInsn(ILOAD, 3);
            mv.visitVarInsn(ALOAD, 1);
            mv.visitMethodInsn(INVOKEINTERFACE, "java/util/List", "size", "()I", true);
            Label l3 = new Label();
            mv.visitJumpInsn(IF_ICMPGE, l3);
            Label l4 = new Label();
            mv.visitLabel(l4);
            mv.visitLineNumber(35, l4);
            mv.visitVarInsn(ALOAD, 1);
            mv.visitVarInsn(ILOAD, 3);
            mv.visitMethodInsn(INVOKEINTERFACE, "java/util/List", "get", "(I)Ljava/lang/Object;", true);
            mv.visitTypeInsn(CHECKCAST, "java/lang/String");
            mv.visitVarInsn(ASTORE, 4);
            Label l5 = new Label();
            mv.visitLabel(l5);
            mv.visitLineNumber(36, l5);
            mv.visitVarInsn(ALOAD, 2);
            mv.visitVarInsn(ALOAD, 4);
            mv.visitTypeInsn(NEW, "java/lang/StringBuilder");
            mv.visitInsn(DUP);
            mv.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);
            mv.visitVarInsn(ALOAD, 4);
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
            mv.visitVarInsn(ALOAD, 4);
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
            mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Map", "put", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", true);
            mv.visitInsn(POP);
            Label l6 = new Label();
            mv.visitLabel(l6);
            mv.visitLineNumber(34, l6);
            mv.visitIincInsn(3, 1);
            mv.visitJumpInsn(GOTO, l2);
            mv.visitLabel(l3);
            mv.visitLineNumber(39, l3);
            mv.visitFrame(Opcodes.F_CHOP, 1, null, 0, null);
            mv.visitVarInsn(ALOAD, 2);
            mv.visitInsn(ARETURN);
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
```

生成的class文件，反编译之后的java文件如下：

```
package com.lzc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Hello {
public static final String FLAG = "我是常量";

    public Hello() {
    }

    public void display() {
        for(int var1 = 0; var1 < 6; ++var1) {
            System.out.println(">>>>>>>>>>我是常量");
        }

    }

    public List<String> testList() {
        ArrayList var1 = new ArrayList();
        var1.add("Tome");
        var1.add("Jack");
        var1.add("Lily");
        System.out.println(">>>>>>>>>>testList > list.size = " + var1.size());
        return var1;
    }

    public Map<String, String> testMap(List<String> list) {
        Map<String, String> map = new HashMap();

        for(int i = 0; i < list.size(); ++i) {
            String str = (String)list.get(i);
            map.put(str, str + str);
        }

        return map;
    }
}

```

## 基于asm篡改一个class文件

本文将以一个实际案例进行讲解，在这里我们假设无法直接去修改MathGame.java，但是我们有MathGame.class文件，计划将MathGame.class进行增强。

```
package com.example.lzcjavaagent;

import org.objectweb.asm.*;
import org.objectweb.asm.commons.AnalyzerAdapter;
import org.objectweb.asm.commons.LocalVariablesSorter;
import org.objectweb.asm.util.TraceClassVisitor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class TimeCountAdpter extends ClassVisitor implements Opcodes {
private String owner;
private boolean isInterface;

    private String methodName;

    private String filedName = "UDASMCN";
    private int acc = Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC + Opcodes.ACC_FINAL;
    private boolean isPresent = false;


    public TimeCountAdpter(ClassVisitor classVisitor) {
        super(ASM6, classVisitor);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
        owner = name;
        isInterface = (access & ACC_INTERFACE) != 0;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        MethodVisitor mv = cv.visitMethod(access, name, descriptor, signature, exceptions);

        if (!isInterface && mv != null && !name.equals("<init>") && !name.equals("<cinit>") && !name.startsWith("java")) {
            methodName = name;

            AddTimerMethodAdapter ca = new AddTimerMethodAdapter(mv);
            AnalyzerAdapter aa = new AnalyzerAdapter(owner, access, name, descriptor, ca);
            LocalVariablesSorter lvs = new LocalVariablesSorter(access, descriptor, aa);

            ca.aa = aa;
            ca.lvs = lvs;

            return ca.lvs;

        }

        return mv;
    }


    @Override
    public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
        if (name.equals(filedName)) {
            isPresent = true;
        }
        return super.visitField(access, name, descriptor, signature, value);
    }


    @Override
    public void visitEnd() {
        if (!isInterface) {
            FieldVisitor fv = cv.visitField(acc, filedName, "Ljava/lang/String;", null, owner);
            if (fv != null) {
                fv.visitEnd();
            }
        }
        cv.visitEnd();
    }

    class AddTimerMethodAdapter extends MethodVisitor {
        private int time;
        private int maxStack;
        public LocalVariablesSorter lvs;
        public AnalyzerAdapter aa;

        public AddTimerMethodAdapter(MethodVisitor methodVisitor) {
            super(ASM6, methodVisitor);
        }


        @Override
        public void visitCode() {
            mv.visitCode();
            mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "nanoTime", "()J", false);
            time = lvs.newLocal(Type.LONG_TYPE);
            mv.visitVarInsn(LSTORE, time);
            maxStack = 4;
        }

        @Override
        public void visitInsn(int opcode) {
            if ((opcode >= IRETURN && opcode <= RETURN) || opcode == ATHROW) {

                mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "nanoTime", "()J", false);
                mv.visitVarInsn(LLOAD, time);
                mv.visitInsn(LSUB);
                mv.visitVarInsn(LSTORE, time);

                mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
                mv.visitTypeInsn(NEW, "java/lang/StringBuilder");
                mv.visitInsn(DUP);
                mv.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);

                mv.visitLdcInsn("    " + owner.replaceAll("/", "\\."));
                mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);

                mv.visitLdcInsn("#" + methodName + ":");
                mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);

                mv.visitVarInsn(LLOAD, time);
                mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(J)Ljava/lang/StringBuilder;", false);

                mv.visitLdcInsn("(ns)");
                mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);

                mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
                mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);

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

            TimeCountAdpter timeCountAdpter = new TimeCountAdpter(traceClassVisitor);

            ClassReader classReader = new ClassReader("demo.MathGame");
            classReader.accept(timeCountAdpter, ClassReader.EXPAND_FRAMES);

            File file = new File("/Users/mac/Download/classes/demo/MathGame.class");
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
```

增强前的MathGame完整代码

```
package demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

//-javaagent:/Users/mac/work/gitstudy/lzcjavaagent/target/lzcjavaagent-0.0.1-SNAPSHOT.jar
public class MathGame {
private static Random random = new Random();

    private int illegalArgumentCount = 0;

    public static void main(String[] args) throws InterruptedException {
        MathGame game = new MathGame();
        while (true) {
            game.run();
            TimeUnit.SECONDS.sleep(2);
        }
    }

    public void run() throws InterruptedException {
        try {
            int x=5;
            int y=2;
            int z=x-y;
            int number = random.nextInt()/1000;
            List<Integer> primeFactors = primeFactors(number);
            print(number, primeFactors);

        } catch (Exception e) {
            System.out.println(String.format("debug illegalArgumentCount:%3d, ", illegalArgumentCount) + e.getMessage());
        }
    }

    public static void print(int number, List<Integer> primeFactors) {
        StringBuffer sb = new StringBuffer(number + "=");
        for (int factor : primeFactors) {
            sb.append(factor).append('*');
        }
        if (sb.charAt(sb.length() - 1) == '*') {
            sb.deleteCharAt(sb.length() - 1);
        }
        System.out.println("debug=="+sb);
    }

    public List<Integer> primeFactors(int number) {
        if (number < 2) {
            illegalArgumentCount++;
            throw new IllegalArgumentException("debug number is: " + number + ", need >= 2");
        }

        List<Integer> result = new ArrayList<Integer>();
        int i = 2;
        while (i <= number) {
            if (number % i == 0) {
                result.add(i);
                number = number / i;
                i = 2;
            } else {
                i++;
            }
        }

        return result;
    }
}
```

增强后的MathGame

```
package demo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MathGame {
private static Random random;
private int illegalArgumentCount = 0;
public static final String UDASMCN = "demo/MathGame";

    public MathGame() {
    }

    public static void main(String[] var0) throws InterruptedException {
        long var1 = System.nanoTime();
        MathGame game = new MathGame();

        while(true) {
            game.run();
            TimeUnit.SECONDS.sleep(2L);
        }
    }

    public void run() throws InterruptedException {
        long var1 = System.nanoTime();

        try {
            int x = 5;
            int y = 2;
            int var10000 = x - y;
            int number = random.nextInt() / 1000;
            List<Integer> primeFactors = this.primeFactors(number);
            print(number, primeFactors);
        } catch (Exception var8) {
            System.out.println(String.format("debug illegalArgumentCount:%3d, ", this.illegalArgumentCount) + var8.getMessage());
        }

        var1 = System.nanoTime() - var1;
        System.out.println("    demo.MathGame" + "#run:" + var1 + "(ns)");
    }

    public static void print(int number, List<Integer> primeFactors) {
        long var2 = System.nanoTime();
        StringBuffer sb = new StringBuffer(number + "=");
        Iterator var5 = primeFactors.iterator();

        while(var5.hasNext()) {
            int factor = (Integer)var5.next();
            sb.append(factor).append('*');
        }

        if (sb.charAt(sb.length() - 1) == '*') {
            sb.deleteCharAt(sb.length() - 1);
        }

        System.out.println("debug==" + sb);
        var2 = System.nanoTime() - var2;
        System.out.println("    demo.MathGame" + "#print:" + var2 + "(ns)");
    }

    public List<Integer> primeFactors(int number) {
        long var2 = System.nanoTime();
        if (number < 2) {
            ++this.illegalArgumentCount;
            IllegalArgumentException var10000 = new IllegalArgumentException("debug number is: " + number + ", need >= 2");
            var2 = System.nanoTime() - var2;
            System.out.println("    demo.MathGame" + "#primeFactors:" + var2 + "(ns)");
            throw var10000;
        } else {
            List<Integer> result = new ArrayList();
            int i = 2;

            while(i <= number) {
                if (number % i == 0) {
                    result.add(i);
                    number /= i;
                    i = 2;
                } else {
                    ++i;
                }
            }

            var2 = System.nanoTime() - var2;
            System.out.println("    demo.MathGame" + "#primeFactors:" + var2 + "(ns)");
            return result;
        }
    }

    static {
        long var0 = System.nanoTime();
        random = new Random();
        var0 = System.nanoTime() - var0;
        System.out.println("    demo.MathGame" + "#<clinit>:" + var0 + "(ns)");
    }
}
```

# JavaAgent

前面已经演示了基于asm增强class文件，接下来进一步演示运行期增强class文件。

## Java Agent定义

从本质上讲，Java Agent 是一个遵循一组严格约定的常规 Java 类。 上面说到 javaagent命令要求指定的类中必须要有premain()方法，并且对premain方法的签名也有要求，签名必须满足以下两种格式：

```
public static void premain(String agentArgs, Instrumentation inst)      
public static void premain(String agentArgs)
```

JVM 会优先加载 带 Instrumentation 签名的方法，加载成功忽略第二种，如果第一种没有，则加载第二种方法。简单来讲，就是 premain 方法，在 main 方法之前执行。

## 如何使用javaagent？

使用 javaagent 需要几个步骤：

1. 定义一个 MANIFEST.MF 文件，必须包含 Premain-Class 选项，通常也会加入Can-Redefine-Classes 和 Can-Retransform-Classes 选项。
2. 创建一个Premain-Class 指定的类，类中包含 premain 方法，方法逻辑由用户自己确定（就是下面示例中的类TraceAgent）。
3. 将 premain 的类和 MANIFEST.MF 文件打成 jar 包。
4. 使用参数 -javaagent: jar包路径 启动要代理的方法。（示例： -javaagent:
   /Users/mac/work/gitstudy/lzcjavaagent/target/lzcjavaagent-0.0.1-SNAPSHOT.jar）

```
package com.example.lzcjavaagent;

import java.lang.instrument.Instrumentation;

public class TraceAgent {

    public static void premain(String agentArgs, Instrumentation instrumentation){
        instrumentation.addTransformer(new LogTransformer());
    }
}
```

```
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
            if(className.startsWith("java")||className.startsWith("sun")||className.startsWith("com/intellij/rt/debugger")){
                return classfileBuffer;
            }

            ClassReader cr = new ClassReader(className);
            ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
            TimeCountAdpter timeCountAdpter = new TimeCountAdpter(cw);
            cr.accept(timeCountAdpter, ClassReader.EXPAND_FRAMES);

            return cw.toByteArray();
        } catch (IOException e) {
            System.out.println("alioo IOException:"+className+":"+e.getMessage());
            e.printStackTrace();
        } catch (Error e) {
            System.out.println("alioo Error:"+className+":"+e.getMessage());
            e.printStackTrace();
        }
        return classfileBuffer;
    }
}


```

除了上述类之外，生成的jar文件中的META-INF/MANIFEST.MF中还需要增加如下内容：
```Premain-Class: com.example.lzcjavaagent.TraceAgent```

示例：

```
~/work/gitstudy/lzcjavaagent/target>more  lzcjavaagent-0.0.1-SNAPSHOT/META-INF/MANIFEST.MF
Manifest-Version: 1.0
Premain-Class: com.example.lzcjavaagent.TraceAgent
Archiver-Version: Plexus Archiver
Built-By: mac
Created-By: Apache Maven 3.6.3
Build-Jdk: 1.8.0_192
```

如果嫌手写比较麻烦的话可以使用maven插件maven-shade-plugin

```
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-shade-plugin</artifactId>
    <executions>
        <execution>
            <phase>package</phase>
            <goals>
                <goal>shade</goal>
            </goals>
            <configuration>
                <transformers>
                    <transformer
                            implementation="org.apache.maven.plugins.shade.resource.ManifestResourceTransformer">
                        <manifestEntries>
                            <Premain-Class>com.example.lzcjavaagent.TraceAgent</Premain-Class>
                        </manifestEntries>
                    </transformer>
                </transformers>
            </configuration>
        </execution>
    </executions>
</plugin>
```

javaagent除了刚才介绍的伴随应用一同启动外，还可以在应用启动之后根据进程id进行attach

```java
package com.example.lzcjavaagent;

import com.sun.tools.attach.*;

import java.io.IOException;
import java.util.List;

/**
 * @author: 悟心
 * @time: 2021/11/2 11:10
 * @description:
 */
public class AttachAfterAppRun {
    public static void main(String[] args) throws AgentLoadException, IOException, AgentInitializationException, AttachNotSupportedException, InterruptedException {
        //获取当前系统中所有 运行中的 虚拟机
        System.out.println("show jvm list");
        List<VirtualMachineDescriptor> list = VirtualMachine.list();
        for (VirtualMachineDescriptor vmd : list) {
            //如果虚拟机的名称为 xxx 则 该虚拟机为目标虚拟机，获取该虚拟机的 pid
            //然后加载 agent.jar 发送给该虚拟机
            System.out.println(">>" + vmd.displayName());
            if (vmd.displayName().endsWith("demo.MathGame")) {

                System.out.println("demo.MathGame matched,pid:" + vmd.id());
                VirtualMachine virtualMachine = VirtualMachine.attach(vmd.id());
                virtualMachine.loadAgent("/Users/mac/work/gitstudy/lzcjavaagent/target/lzcjavaagent-0.0.1-SNAPSHOT.jar");
                virtualMachine.detach();
                System.out.println("attach done");
            }
        }
        Thread.sleep(15000L);
    }
}

```

几个注意事项：

- pom.xml中需要添加tools.jar的依赖

```xml

<dependency>
    <groupId>com.sun</groupId>
    <artifactId>tools</artifactId>
    <version>1.8.0</version>
    <scope>system</scope>
    <systemPath>/Library/Java/JavaVirtualMachines/jdk1.8.0_192.jdk/Contents/Home/lib/tools.jar</systemPath>
</dependency>
```

- pom.xml中插件maven-shade-plugin添加如下内容
```xml
<manifestEntries>
   <Premain-Class>com.example.lzcjavaagent.TraceAgent</Premain-Class>
   <Agent-Class>com.example.lzcjavaagent.TraceAgent</Agent-Class>
   <Can-Redefine-Classes>true</Can-Redefine-Classes>
   <Can-Retransform-Classes>true</Can-Retransform-Classes>
</manifestEntries>
```

- 增强逻辑中之前是有添加类变量的逻辑,需要去掉一下，否则会报下面这个错误

```
Caused by: java.lang.UnsupportedOperationException: class redefinition failed: attempted to change the schema (add/remove fields)
```

本文完整的代码git地址：https://github.com/lzc-alioo/lzcjavaagent，欢迎下载运行。

参考文章：
https://www.cnblogs.com/rickiyang/p/11368932.html
https://blog.csdn.net/catoop/article/details/50629921
https://blog.csdn.net/wenwen513/article/details/86498687