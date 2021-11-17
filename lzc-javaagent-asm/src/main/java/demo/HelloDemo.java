package demo;

/**
 * @author: 悟心
 * @time: 2021/11/2 15:29
 * @description:
 */
public class HelloDemo {
    public static void main(String[] args) throws InterruptedException {
        while (true) {
            new Hello().testList();
            Thread.sleep(2000L);
        }
    }
}
