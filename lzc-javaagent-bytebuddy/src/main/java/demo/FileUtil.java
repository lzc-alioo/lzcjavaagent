package demo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author: 悟心
 * @time: 2021/11/10 00:12
 * @description:
 */
public class FileUtil {
    public static void outputClazz(byte[] bytes,String classFilePath) {

        try {
//            File file = new File("/Users/mac/Download/classes/demo/Hello.class");
//            File file = new File("/Users/mac/work/gitstudy/lzcjavaagent/lzc-javaagent-asm/target/classes/lzc/demo/MathGame.class");
            File file = new File(classFilePath);
            String parent = file.getParent();
            File parent1 = new File(parent);
            boolean mkdirs = parent1.mkdirs();
            System.out.println("mkdirs=" + mkdirs + ",exist=" + file.exists());
            boolean createNewFile = file.createNewFile();
            System.out.println("mkdirs=" + mkdirs + ",createNewFile=" + createNewFile + ",exist=" + file.exists());

            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(bytes);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }
}
